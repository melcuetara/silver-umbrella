package com.example.rentaland.model;

public class viewReportModel {
    public String name;
    public String contactNumber,userType,body;

    public viewReportModel(String name, String contactNumber, String userType, String body) {
        this.name = name;
        this.contactNumber = contactNumber;
        this.userType = userType;
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
