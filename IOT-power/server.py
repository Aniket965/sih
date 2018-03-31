import requests
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import time
cred = credentials.Certificate('admin.json')
default_app = firebase_admin.initialize_app(
    cred, {'databaseURL': 'https://not-so-awesome-project-45a2e.firebaseio.com'})
database_ref = db.reference('/IOTdata')

url = "http://localhost:4000/lightstatus"
prev_response = "0"
while True:
    response = requests.get(url)
    if response.text != prev_response:
        ts = time.time()
        database_ref.push(
            {
                "roomid": 1,
                "applianceid": '123',
                "status": response.text,
                "timestamp_UNIX": ts
            })
    print("status changed")
    prev_response = response.text
    time.sleep(1);
    print(response.text)

