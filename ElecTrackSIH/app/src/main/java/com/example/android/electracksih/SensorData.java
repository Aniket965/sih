package com.example.android.electracksih;

/**
 * Created by NITANT SOOD on 30-03-2018.
 */

public class SensorData {
String PIR,Temp,Humidity,LDR,Curr1,Curr2,RoomNo;
Long timeOfReading;

    public SensorData(String PIR, String temp, String humidity, String LDR, String curr1, String curr2, String roomNo,Long timeOfReading) {
        this.PIR = PIR;
        Temp = temp;
        Humidity = humidity;
        this.LDR = LDR;
        Curr1 = curr1;
        Curr2 = curr2;
        RoomNo = roomNo;
        this.timeOfReading=timeOfReading;
    }

    public Long getTimeOfReading() {
        return timeOfReading;
    }

    public void setTimeOfReading(Long timeOfReading) {
        this.timeOfReading = timeOfReading;
    }

    public String getRoomNo() {
        return RoomNo;
    }

    public void setRoomNo(String roomNo) {
        RoomNo = roomNo;
    }

    public String getPIR() {
        return PIR;
    }

    public void setPIR(String PIR) {
        this.PIR = PIR;
    }

    public String getTemp() {
        return Temp;
    }

    public void setTemp(String temp) {
        Temp = temp;
    }

    public String getHumidity() {
        return Humidity;
    }

    public void setHumidity(String humidity) {
        Humidity = humidity;
    }

    public String getLDR() {
        return LDR;
    }

    public void setLDR(String LDR) {
        this.LDR = LDR;
    }

    public String getCurr1() {
        return Curr1;
    }

    public void setCurr1(String curr1) {
        Curr1 = curr1;
    }

    public String getCurr2() {
        return Curr2;
    }

    public void setCurr2(String curr2) {
        Curr2 = curr2;
    }
}
