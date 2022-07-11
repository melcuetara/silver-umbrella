package com.example.rentaland.model;

public class IdModel {

    private String idType;
    private String idImageUrl;

    public IdModel(String idType, String idImageUrl) {
        this.idType = idType;
        this.idImageUrl = idImageUrl;
    }

    public IdModel(String idType) {
        this.idType = idType;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getIdImageUrl() {
        return idImageUrl;
    }

    public void setIdImageUrl(String idImageUrl) {
        this.idImageUrl = idImageUrl;
    }
}
