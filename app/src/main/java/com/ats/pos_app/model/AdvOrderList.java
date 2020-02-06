package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvOrderList {

    @SerializedName("uid")
    @Expose
    private String uid;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("total")
    @Expose
    private Integer total;

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

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "AdvOrderList{" +
                "uid='" + uid + '\'' +
                ", orderDate='" + orderDate + '\'' +
                ", total=" + total +
                '}';
    }
}
