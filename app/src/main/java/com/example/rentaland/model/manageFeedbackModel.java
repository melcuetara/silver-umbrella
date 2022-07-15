package com.example.rentaland.model;

public class manageFeedbackModel {
    public String name,address,contactNumber,userType,userUID,body;

    public manageFeedbackModel(String name, String address, String contactNumber, String userType, String userUID, String body) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.userType = userType;
        this.userUID = userUID;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
