package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyMartList {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("total")
    @Expose
    private float total;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "DailyMartList{" +
                "uid='" + uid + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", total=" + total +
                '}';
    }
}
