package com.example.rentaland.model;

import android.provider.Contacts;

public class viewFarmerGovModel {
    private String name,address,contactNumber,age,gender,farmlandName,farmlandAddress,farmBudget,farmArea,UID;
    private String farmlandUrl,imageUrl;

    public viewFarmerGovModel(String name, String address, String contactNumber, String age, String gender, String farmlandName, String farmlandAddress, String farmBudget, String farmArea, String farmlandUrl, String imageUrl,String UID) {
        this.name = name;
        this.address = address;
        this.contactNumber = contactNumber;
        this.age = age;
        this.gender = gender;
        this.farmlandName = farmlandName;
        this.farmlandAddress = farmlandAddress;
        this.farmBudget = farmBudget;
        this.farmArea = farmArea;
        this.farmlandUrl = farmlandUrl;
        this.imageUrl = imageUrl;
        this.UID = UID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFarmlandName() {
        return farmlandName;
    }

    public void setFarmlandName(String farmlandName) {
        this.farmlandName = farmlandName;
    }

    public String getFarmlandAddress() {
        return farmlandAddress;
    }

    public void setFarmlandAddress(String farmlandAddress) {
        this.farmlandAddress = farmlandAddress;
    }

    public String getFarmBudget() {
        return farmBudget;
    }

    public void setFarmBudget(String farmBudget) {
        this.farmBudget = farmBudget;
    }

    public String getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(String farmArea) {
        this.farmArea = farmArea;
    }

    public String getFarmlandUrl() {
        return farmlandUrl;
    }

    public void setFarmlandUrl(String farmlandUrl) {
        this.farmlandUrl = farmlandUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
