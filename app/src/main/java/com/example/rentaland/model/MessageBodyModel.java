package com.example.rentaland.model;

public class MessageBodyModel {

    private String message;
    private String time;
    private String investorId;
    private String farmerId;

    public MessageBodyModel(String message, String time, String investorId, String farmerId) {
        this.message = message;
        this.time = time;
        this.investorId = investorId;
        this.farmerId = farmerId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
}
