package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrGraph {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("sellDate")
    @Expose
    private String sellDate;
    @SerializedName("sellAmount")
    @Expose
    private String sellAmount;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSellDate() {
        return sellDate;
    }

    public void setSellDate(String sellDate) {
        this.sellDate = sellDate;
    }

    public String getSellAmount() {
        return sellAmount;
    }

    public void setSellAmount(String sellAmount) {
        this.sellAmount = sellAmount;
    }

    @Override
    public String toString() {
        return "FrGraph{" +
                "uid='" + uid + '\'' +
                ", sellDate='" + sellDate + '\'' +
                ", sellAmount='" + sellAmount + '\'' +
                '}';
    }
}
