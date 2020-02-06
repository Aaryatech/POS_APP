package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PieItem {

    @SerializedName("itemId")
    @Expose
    private Integer itemId;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemTotal")
    @Expose
    private String itemTotal;

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemTotal() {
        return itemTotal;
    }

    public void setItemTotal(String itemTotal) {
        this.itemTotal = itemTotal;
    }

    @Override
    public String toString() {
        return "PieItem{" +
                "itemId=" + itemId +
                ", itemName='" + itemName + '\'' +
                ", itemTotal='" + itemTotal + '\'' +
                '}';
    }
}
