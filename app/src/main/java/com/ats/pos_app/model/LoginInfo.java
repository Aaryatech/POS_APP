package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginInfo {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("accessRight")
    @Expose
    private Integer accessRight;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(Integer accessRight) {
        this.accessRight = accessRight;
    }

    @Override
    public String toString() {
        return "LoginInfo{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", accessRight=" + accessRight +
                '}';
    }
}
