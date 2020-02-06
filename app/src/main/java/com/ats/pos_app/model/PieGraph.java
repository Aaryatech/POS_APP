package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PieGraph {

    @SerializedName("catId")
    @Expose
    private Integer catId;
    @SerializedName("catName")
    @Expose
    private String catName;
    @SerializedName("catTotal")
    @Expose
    private String catTotal;

    public Integer getCatId() {
        return catId;
    }

    public void setCatId(Integer catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatTotal() {
        return catTotal;
    }

    public void setCatTotal(String catTotal) {
        this.catTotal = catTotal;
    }

    @Override
    public String toString() {
        return "PieGraph{" +
                "catId=" + catId +
                ", catName='" + catName + '\'' +
                ", catTotal='" + catTotal + '\'' +
                '}';
    }
}
