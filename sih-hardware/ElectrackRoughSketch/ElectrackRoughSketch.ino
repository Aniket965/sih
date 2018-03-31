#include <ESP8266WiFi.h>
#include <ESP8266WebServer.h>
#include <ESP8266HTTPClient.h>

const char* ssid = "yash";
const char* password = "andromeda";

const int noOfAppliances = 4;
int pir = D0;
int inputPins[noOfAppliances] = {D1, D2, D3, D4};
int outputPins[noOfAppliances] = {D5, D6, D7, D8};
int switchValue[noOfAppliances] = {0, 0, 0, 0};
int lastSwitchValue[noOfAppliances] = {0, 0, 0, 0};
int finalControl[noOfAppliances] = {0, 0, 0, 0};
int appData[noOfAppliances] = {0, 0, 0, 0};

ESP8266WebServer server(80);

void lightStatus() { //Handler for the path
    String message = String("105,1,") + finalControl[0] + String(";104,1,") + finalControl[1] + String(";100,1,") + finalControl[2] + String(";102,1,") + finalControl[3];
    server.send(200, "text/plain", message);
}

void setup() {
    int i;
    Serial.begin(115200);
    delay(1000);

    // Initialize all pins
    pinMode(pir, INPUT);
    for(i = 0; i < noOfAppliances; i++) {
        pinMode(inputPins[i], INPUT);
        pinMode(outputPins[i], OUTPUT);
        digitalWrite(outputPins[i], LOW);
    }

    Serial.println();
    Serial.println();
    Serial.print("Connecting to: ");
    Serial.println(ssid);

    WiFi.begin(ssid, password);

    while(WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.println("Wifi connected");

    server.on("/lightstatus", lightStatus);
    server.begin();
    Serial.println("Server started");

    Serial.print("Use this url to connect: ");
    Serial.print("http://");
    Serial.print(WiFi.localIP());
    Serial.println("/");
}

void loop() {
    int i, pirData, pirPreviousData, controlAppliance[3] = {0, 0, 0};
    int Part = 0;

    // Check the status of all input devices
    for(i = 0; i < noOfAppliances; i++)
        switchValue[i] = digitalRead(inputPins[i]);
    pirData = digitalRead(pir);
    Serial.println("");

    for(i = 0; i < noOfAppliances; i++) {
        if(i < 3)       // Light and fan
            finalControl[i] = switchValue[i] && pirData;
        else
            finalControl[i] = switchValue[i];
    }
    
    if (WiFi.status() == WL_CONNECTED) {            //Check WiFi connection status
        server.handleClient();                      //handle client requests
        
        HTTPClient http;                            //Declare an object of class HTTPClient
 
        http.begin("http://192.168.43.189:5000/esp"); //Specify request destination
        
        int httpCode = http.GET();                  //Send the request
        Serial.print("Http Code: ");
        Serial.println(httpCode);
        if (httpCode > 0) {                         //Check the returning code
 
            String payload = http.getString();      //Get the request response payload
            Serial.println(payload);                //Print the response payload
            for ( int i=0; i<7; i++ )
            {
                char c = payload[i];
                if ( c == ',' )
                {
                    Part++;
                    continue;
                }
                controlAppliance[Part] *= 10;
                controlAppliance[Part] += c - '0';
            }
//            Serial.print(controlAppliance[0]%100);
//            Serial.println(controlAppliance[2]);
//            
//            digitalWrite(controlAppliance[0]%100, controlAppliance[2]);

            Serial.print("FinalControl: ");
            for (i = 0; i < noOfAppliances; i++)
            {
                Serial.print("TP1: ");
                Serial.print(outputPins[i]);
                Serial.println(controlAppliance[0]);
                if(outputPins[i] == controlAppliance[0]%100)
                {
                    appData[i] = controlAppliance[2];
                }
                Serial.print(appData[i]);
                Serial.print(" ");
            }
            Serial.println("");
            
            // Compare strings and give outputs (Output logic over here)
            
        }
        http.end();   //Close connection
    }
    pirPreviousData = pirData;
    for (i = 0; i < noOfAppliances; i++) {
        lastSwitchValue[i] = switchValue[i];
        digitalWrite(outputPins[i], finalControl[i] && appData[i]);
    }
}
