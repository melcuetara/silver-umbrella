package com.example.rentaland.model;

import com.google.firebase.database.Exclude;

public class FarmModel {

    private String farmAddress;
    private double farmArea;
    private String farmName;
    private String farmImageUrl;
    private double farmingBudget;
    private String farmerId;
    private String farmTitleImageUrl;

    public String getFarmTitleImageUrl() {
        return farmTitleImageUrl;
    }

    public void setFarmTitleImageUrl(String farmTitleImageUrl) {
        this.farmTitleImageUrl = farmTitleImageUrl;
    }

    public FarmModel() { };

    public FarmModel(String farmAddress, double farmArea, String farmName, String farmImageUrl, double farmingBudget, String farmerId) {
        this.farmAddress = farmAddress;
        this.farmArea = farmArea;
        this.farmName = farmName;
        this.farmImageUrl = farmImageUrl;
        this.farmingBudget = farmingBudget;
        this.farmerId = farmerId;
    }

    public FarmModel(String farmAddress, double farmArea, String farmName, double farmingBudget, String uid) {
        this.farmAddress = farmAddress;
        this.farmArea = farmArea;
        this.farmName = farmName;
        this.farmingBudget = farmingBudget;
        farmerId = uid;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public double getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(double farmArea) {
        this.farmArea = farmArea;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmImageUrl() {
        return farmImageUrl;
    }

    public void setFarmImageUrl(String farmImageUrl) {
        this.farmImageUrl = farmImageUrl;
    }

    public double getFarmingBudget() {
        return farmingBudget;
    }

    public void setFarmingBudget(double farmingBudget) {
        this.farmingBudget = farmingBudget;
    }

    @Exclude
    public String getValue() {
        return getFarmAddress() + getFarmName() + getFarmImageUrl() + getFarmArea() + getFarmArea();
    }
}
