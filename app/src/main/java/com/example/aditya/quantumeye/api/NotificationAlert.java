package com.example.aditya.quantumeye.api;

import com.google.gson.annotations.SerializedName;

public class NotificationAlert {

    @SerializedName("image")
    String imageUrl;

    @SerializedName("message")
    String message;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
