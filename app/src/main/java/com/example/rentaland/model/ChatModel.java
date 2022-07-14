package com.example.rentaland.model;

public class ChatModel {

    private String body;
    private String date;
    private String sender;

    public ChatModel() {

    }
    public ChatModel(String body, String date, String uid) {
        this.body = body;
        this.date = date;
        sender = uid;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
