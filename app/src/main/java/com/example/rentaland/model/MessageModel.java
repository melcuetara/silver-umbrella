package com.example.rentaland.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MessageModel implements Parcelable {

    private String body;
    private String sender;
    private String date;

    public MessageModel(String body, String sender, String date) {
        this.body = body;
        this.sender = sender;
        this.date = date;
    }

    public MessageModel() {}
    protected MessageModel(Parcel in) {
        body = in.readString();
        sender = in.readString();
        date = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(body);
        dest.writeString(sender);
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MessageModel> CREATOR = new Creator<MessageModel>() {
        @Override
        public MessageModel createFromParcel(Parcel in) {
            return new MessageModel(in);
        }

        @Override
        public MessageModel[] newArray(int size) {
            return new MessageModel[size];
        }
    };

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
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
}
