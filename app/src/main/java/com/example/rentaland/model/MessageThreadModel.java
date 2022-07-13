package com.example.rentaland.model;

import java.util.ArrayList;

public class MessageThreadModel {

    private String farmerId;
    private String investorId;
    private ArrayList<MessageModel> message;

    public String getFarmerId() {
        return farmerId;
    }

    public void setFarmerId(String farmerId) {
        this.farmerId = farmerId;
    }

    public String getInvestorId() {
        return investorId;
    }

    public void setInvestorId(String investorId) {
        this.investorId = investorId;
    }

    public ArrayList<MessageModel> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<MessageModel> message) {
        this.message = message;
    }
}
