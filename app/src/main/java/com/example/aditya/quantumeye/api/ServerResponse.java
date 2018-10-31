package com.example.aditya.quantumeye.api;

import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("message")
    String message;

    @SerializedName("success")
    boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}