import pyodbc as db

""" 
Configured for MS SQL Server instances only
For MySQL or PostgreSQL refer to ...
"""

"""
Default server is always localhost from cloud_sql_proxy
Connection architecture is in the form of: python <=> cloud_sql_proxy <=> Cloud SQL Server Instance
"""
def connect_to_db(database, userID, password):
    
    conn = db.connect('DRIVER={SQL Server}; SERVER=127.0.0.1; DATABASE={}; UID={}; PWD={}').format(database, userID, password)

# supply a query
def query_db(query_string):
    
    cursor.execute("{}").format(query_string)
                      
    for row in cursor.fetchall():
        print(row)
        
        