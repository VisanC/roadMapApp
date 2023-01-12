package com.example.myapplication3;

public class SendorDataModel {

    String location;
    String accelerationData;
    String time;

    public SendorDataModel( String location, String accelerationData, String time) {

        this.location = location;
        this.accelerationData = accelerationData;
        this.time = time;
    }

    public SendorDataModel() {
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAccelerationData() {
        return accelerationData;
    }

    public void setAccelerationData(String accelerationData) {
        this.accelerationData = accelerationData;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
