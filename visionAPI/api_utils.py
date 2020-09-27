import io
import os
import pickle
import codecs
from datetime import datetime
from google.cloud import storage, vision
from google.cloud.vision import types
from shapely.geometry import Polygon

# Set environment variable with your GCP secret key file
os.environ['GOOGLE_APPLICATION_CREDENTIALS']='key.json'

# Configuration for extract_text_local
img_dir = r'images'

# Store list of file names for easy access
img_files = [f for f in os.listdir(img_dir)] # alternatively use if searching for specific extension [f for f in os.listdir(img_dir) if and f.endswith('.jpg')]

# Instantiate resource
client = vision.ImageAnnotatorClient()

# For local images
def extract_general_text_local(image):

    with io.open(image, 'rb') as image_file:
        content = image_file.read()
    
    image = vision.types.Image(content=content)
    
    response = client.text_detection(image=image)
    text = response.text_annotations
    
    print('\nText Extraction Results...\n')
    print('===' * 10)
    for values in text:
        print(values.description)
        
        locations = (['({}, {})'.format(vertex.x, vertex.y) for vertex in values.bounding_poly.vertices])
        print('location bounds: {}'.format(','.join(locations)))
    
# For remote image files in Google Cloud Storage
def extract_general_text_cloud(image_uri):
    
    image = vision.types.Image()
    image.source.image_uri = image_uri
    
    response = client.text_detection(image=image)
    text = response.text_annotations
    
    print('\nText Extraction Results...\n')
    print('===' * 10)
    for values in text:
        print(text.description)
        
        locations = (['({}, {})'.format(vertex.x, vertex.y) for vertex in text.bounding_poly.vertices])
        print('location bounds: {}'.format(','.join(locations)))    

def extract_license_plate(image):
    
    with io.open(image, 'rb') as image_file:
        content = image_file.read()
    
    image = vision.types.Image(content=content)
    
    response = client.text_detection(image=image)
    text = response.text_annotations
    
    print('\nText Extraction Processing...\n')
    print('===' * 10)
    
    count = 0
    text_contents_list = []
    for values in text:
        full_text = text[0].description
        text_contents_list.append(values.description)
        # print(values.description)
        count += 1
        locations = (['({}, {})'.format(vertex.x, vertex.y) for vertex in values.bounding_poly.vertices])
        # print('location bounds: {}'.format(','.join(locations)))
    
    # Separate by delimiter = '\n'     
    full_text_list = full_text.split('\n')
    reduced_list = [substrings for substrings in full_text_list if len(substrings) in range (4,10)]
    
    indices = []
    for i in range(count):
        if text[i].description in reduced_list:
            indices.append(i) 
    
    indices_list = []
    for val in indices:
        indices_list.append(text[val].description)
    
    # Reference list used for else case below
    ref_list_boolean = [item in reduced_list for item in text_contents_list] 
    ref_list_indices = []
    for index in range(len(ref_list_boolean)):
        if ref_list_boolean[index] == True:
            ref_list_indices.append(index)
                
    final_res = []
    reduced_list_coordinates = []
    cleaned_coordinates = []
    areas = []
    
    # Indication that License Plate Values are split
    # Return value as final result 
    if len(indices) < len(reduced_list):
        final_res = list(set(reduced_list) - set(indices_list))
        final_res = final_res[0].strip().replace(" ", "")
        print("\nLicense Plate for Image is: {}\n".format(final_res))
        file = codecs.open("{}".format(datetime.now().strftime("%Y-%m-%d-%H-%M-%S"))+".txt","w", "utf-8")
        file.write(final_res)
        file.close()
    
    # Final case; identify License Plate Value by text size
    # i.e., Area of Bounding Polygon   
    elif len(indices) >= len(reduced_list):
        for index in ref_list_indices:
            reduced_list_coordinates.append([[(vertex.x, vertex.y)] for vertex in text[index].bounding_poly.vertices])
         
        for i in range(len(reduced_list_coordinates)):
            temp = []
            for j in range(len(reduced_list_coordinates[i])):
                temp.append(reduced_list_coordinates[i][j][0])
            cleaned_coordinates.append(temp)
            
        for coords in range(len(cleaned_coordinates)):
            areas.append(Polygon(cleaned_coordinates[coords]).area)
            max_index = areas.index(max(areas))
            final_res = text_contents_list[max_index].strip().replace(" ", "")
        print("\nLicense Plate for Image is: {}\n".format(final_res))
        file = codecs.open("{}.txt".format(datetime.now().strftime("%Y-%m-%d-%H-%M-%S")), "w", "utf-8")
        file.write(final_res)
        file.close()
        
def save_to_cloud(bucket_name, file_name, destination_name):
    
    storage_client = storage.Client()
    bucket = storage_client.get_bucket(bucket_name)
    blob = bucket.blob(destination_name)
    
    blob.upload_from_filename(file_name)
    
    print('File {} successfully uploaded to Google Storage Bucket: {}.'.format(destination_name, bucket_name))
    