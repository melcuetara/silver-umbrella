package com.example.rentaland.model;

public class FarmModel {

    private String farmAddress;
    private double farmArea;
    private String farmName;
    private String farmImageUrl;
    private double farmingBudget;

    public FarmModel() { };

    public FarmModel(String farmAddress, double farmArea, String farmName, String farmImageUrl, double farmingBudget) {
        this.farmAddress = farmAddress;
        this.farmArea = farmArea;
        this.farmName = farmName;
        this.farmImageUrl = farmImageUrl;
        this.farmingBudget = farmingBudget;
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
}
