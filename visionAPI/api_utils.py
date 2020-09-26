import io
import os

# Imports the Google Cloud client library
from google.cloud import vision
from google.cloud.vision import types

os.environ['GOOGLE_APPLICATION_CREDENTIALS']='key.json'

# Instantiates a client
client = vision.ImageAnnotatorClient()

# The name of the image file to annotate
file_name = os.path.abspath(r'C:/Users/Eitan Flor/Desktop/pic.jpg')

# Loads the image into memory
with io.open(file_name, 'rb') as image_file:
    content = image_file.read()

image = types.Image(content=content)

# Performs label detection on the image file

responseText = client.text_detection(image=image)
responseObject = client.localized_object_annotation

testy = client.object_localization(image=image)

print('Text Extraction Results...')
for text in testy.localized_object_annotations:
    print(text)
