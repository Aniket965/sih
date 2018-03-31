#include <dht.h>
#include <Filters.h>

float testFrequency = 50;                     // test signal frequency (Hz)
float windowLength = 20.0/testFrequency;     // how long to average the signal, for statistist
int currentValue1 = 0, currentValue2 = 0;
float intercept = -0.1429; // to be adjusted based on calibration testing
float slope = 0.12; // to be adjusted based on calibration testing
float current_amps1, current_amps2; // estimated actual current in amps

unsigned long printPeriod = 1000; // in milliseconds
// Track time in milliseconds since last reading 
unsigned long previousMillis = 0;

dht DHT;

int PIRpin = 2;
int DHTpin = 5;
int currentSensor1 = A0, currentSensor2 = A1;
int LDRpin = A2;

void setup() {
  // put your setup code here, to run once:
    Serial.begin(115200);
    pinMode(DHTpin, INPUT);
    pinMode(PIRpin, INPUT);
}

void loop() {
    RunningStatistics inputStats1, inputStats2;                 // create statistics to look at the raw test signal
    inputStats1.setWindowSecs( windowLength );
    inputStats2.setWindowSecs( windowLength );
    
    while(true) {
        currentValue1 = analogRead(currentSensor1);  // read the analog in value
        currentValue2 = analogRead(currentSensor2);
        inputStats1.input(currentValue1);  // log to Stats function
        inputStats2.input(currentValue2);
        
        unsigned long present = millis(), PIRone;
        int chk = DHT.read11(DHTpin);
        //Serial.print(chk);
        int LDRdata = analogRead(LDRpin);
        int PIRdata = digitalRead(PIRpin);
        int PIRout;
        int temper, humid;
        if (chk == 0) {
            temper = DHT.temperature;
            humid = DHT.humidity;
        }
        if(PIRdata == 1)
        {
            PIRone = present;
            PIRout = PIRdata;
        }
        else if(PIRdata == 0 && present - PIRone < 10000)
            PIRout = 1;
        else
            PIRout = 0;

        if((unsigned long)(present - previousMillis) >= printPeriod) {
            previousMillis = present;   // update time
            Serial.print(PIRout);
            Serial.print(",");
            Serial.print(temper);
            Serial.print(",");
            Serial.print(humid);
            Serial.print(",");
            Serial.print(LDRdata);
            Serial.print(",");
            current_amps1 = intercept + slope * inputStats1.sigma();
            current_amps2 = intercept + slope * inputStats2.sigma();
            Serial.print(current_amps1);
            Serial.print(",");
            Serial.print(current_amps2);
            Serial.print("\n");
        }
    }
} 
