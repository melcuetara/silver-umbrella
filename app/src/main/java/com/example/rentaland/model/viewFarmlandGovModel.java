package com.example.rentaland.model;

public class viewFarmlandGovModel {
    private String farmlandName,address,farmlandBudget,farmlandArea,farmlandImg;

    public viewFarmlandGovModel(String farmlandName, String address, String farmlandBudget, String farmlandArea, String farmlandImg) {
        this.farmlandName = farmlandName;
        this.address = address;
        this.farmlandBudget = farmlandBudget;
        this.farmlandArea = farmlandArea;
        this.farmlandImg = farmlandImg;
    }

    public String getFarmlandName() {
        return farmlandName;
    }

    public void setFarmlandName(String farmlandName) {
        this.farmlandName = farmlandName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFarmlandBudget() {
        return farmlandBudget;
    }

    public void setFarmlandBudget(String farmlandBudget) {
        this.farmlandBudget = farmlandBudget;
    }

    public String getFarmlandArea() {
        return farmlandArea;
    }

    public void setFarmlandArea(String farmlandArea) {
        this.farmlandArea = farmlandArea;
    }

    public String getFarmlandImg() {
        return farmlandImg;
    }

    public void setFarmlandImg(String farmlandImg) {
        this.farmlandImg = farmlandImg;
    }
}
