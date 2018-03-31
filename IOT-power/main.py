from flask import Flask,render_template,jsonify,request,redirect
from PowerUsage import PowerUsage
from Classifiers import Classifiers
import firebase_admin
from firebase_admin import credentials
from firebase_admin import db
import apiai
import time
import json
import serial
# ardiuno = serial.Serial("COM5",9600)

cred = credentials.Certificate('admin.json')
default_app = firebase_admin.initialize_app(cred,{'databaseURL' : 'https://not-so-awesome-project-45a2e.firebaseio.com'})
database_ref = db.reference('/IOTdata')
sensor_red = db.reference('/sensors')

app = Flask(__name__)

####### API AI CONFIG ###########
# Client Access Token for accessing our API AI Bot
CLIENT_ACCESS_TOKEN = '2f81ae08dd2d4890b6413565a88577cd'
ai = apiai.ApiAI(CLIENT_ACCESS_TOKEN)

# print(response)

@app.route('/')
def index():
    return "welcome to Power Usage api go to /api for refrence"

sample_api_output =  [
    {
        "roomid":1,
        "applianceid":'123',
        "status":"1",
        "timestamp_UNIX":""
    },
       {
        "roomid":2,
        "noOfPeople":PowerUsage().getData()
    }
]
@app.route('/api',methods=['GET'])
def api():
    return jsonify({'data':sample_api_output})
arg1,arg2,arg3="","",""
@app.route('/lightturnedon')
def lightturnedon():
    ts = time.time()
    arg1= request.args['arg1']
    arg2= request.args['arg2']
    arg3= request.args['arg3']
    database_ref.push(
        {
        "roomid":arg1,
        "applianceid":arg2,
        "status":arg3,
        "timestamp_UNIX":ts
    }
    )
    args = arg2+","+arg1+","+arg3
    with open('file.txt', 'w') as fh:
        fh.write(args)
    return redirect('http://localhost:5000/esp')

@app.route('/lightturnedoff')
def lightturnedoff():
    ts = time.time()
    arg1= request.args['arg1']
    arg2= request.args['arg2']
    arg3= request.args['arg3']
    database_ref.push(
        {
        "roomid":arg1,
        "applianceid":arg2,
        "status":arg3,
        "timestamp_UNIX":ts
    }
    )
    args = arg2+","+arg1+","+arg3
    with open('file.txt', 'w') as fh:
        fh.write(args)
    return redirect('http://localhost:5000/esp')

@app.route('/esp', methods=['GET'])
def esp():
    with open('file.txt') as f:
        return f.read()

'''
For getting SensorData from Ardiuno

'''
@app.route('/sensor_data')
def sensor_data():
    # result = ardiuno.realine()
    # sensor_red.push({
    #     "temp":"3",
    #     "pir":"1",
    #     "humidity":"900",
    #     "light":""

    # })
    return result

'''
For getting Predicted Power from time
'''
@app.route('/predictpower')
def predictpower():
    m = int(request.args['month'])
    data = []
    for i in range(0,24):
        predicted = Classifiers().predict_Power_from_date(i,m)
        data.append(predicted[0])
    return str(data)

@app.route('/bot')
def bot():
    q = request.args['q']
    req = ai.text_request()
    req.query = q
    res = json.loads(req.getresponse().read().decode('utf-8'))
    responseStatus = res['status']['code']
    if (responseStatus == 200):
        # Sending the textual response of the bot.
        print(res['result'])
        return (res['result']['fulfillment']['speech'])
    else:
        return ("Sorry, I couldn't understand that question")

if __name__ == '__main__':
    app.run(host='0.0.0.0', debug=True)