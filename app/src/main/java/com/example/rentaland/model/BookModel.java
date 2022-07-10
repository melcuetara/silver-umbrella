package com.example.rentaland.model;

public class BookModel {

    private String investorId;
    private String farmerId;
    private String timeStamp;

    public BookModel(String investorId, String farmerId, String timeStamp) {
        this.investorId = investorId;
        this.farmerId = farmerId;
        this.timeStamp = timeStamp;
    }

    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
}
