package com.sendpost.dreamsoft.responses;

import com.google.gson.annotations.SerializedName;

public class SimpleResponse {

    @SerializedName("code")
    public int code;

    @SerializedName("message")
    public String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
