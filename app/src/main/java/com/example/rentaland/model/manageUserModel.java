package com.example.rentaland.model;

public class manageUserModel {
    public String name,address,contact,userType,imageUrl,userUID;

    public manageUserModel(String name, String address, String contact, String userType, String imageUrl,String userUID) {
        this.name = name;
        this.address = address;
        this.contact = contact;
        this.userType = userType;
        this.imageUrl = imageUrl;
        this.userUID = userUID;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
