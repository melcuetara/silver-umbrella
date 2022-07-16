package com.example.rentaland.model;

public class editFarmlandModel {
    private  String farmAddress,farmImageUrl,farmName,farmTitleImageUrl,farmerId;
    private Double farmArea,farmingBudget;

    public editFarmlandModel(String farmAddress, String farmImageUrl, String farmName, String farmTitleImageUrl, String farmerId, Double farmArea, Double farmingBudget) {
        this.farmAddress = farmAddress;
        this.farmImageUrl = farmImageUrl;
        this.farmName = farmName;
        this.farmTitleImageUrl = farmTitleImageUrl;
        this.farmerId = farmerId;
        this.farmArea = farmArea;
        this.farmingBudget = farmingBudget;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }

    public String getFarmImageUrl() {
        return farmImageUrl;
    }

    public void setFarmImageUrl(String farmImageUrl) {
        this.farmImageUrl = farmImageUrl;
    }

    public String getFarmName() {
        return farmName;
    }

    public void setFarmName(String farmName) {
        this.farmName = farmName;
    }

    public String getFarmTitleImageUrl() {
        return farmTitleImageUrl;
    }

    public void setFarmTitleImageUrl(String farmTitleImageUrl) {
        this.farmTitleImageUrl = farmTitleImageUrl;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public Double getFarmArea() {
        return farmArea;
    }

    public void setFarmArea(Double farmArea) {
        this.farmArea = farmArea;
    }

    public Double getFarmingBudget() {
        return farmingBudget;
    }

    public void setFarmingBudget(Double farmingBudget) {
        this.farmingBudget = farmingBudget;
    }
}
