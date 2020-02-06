package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrSetting {

    @SerializedName("frSettingId")
    @Expose
    private Integer frSettingId;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("frCode")
    @Expose
    private String frCode;
    @SerializedName("sellBillNo")
    @Expose
    private Integer sellBillNo;
    @SerializedName("grnGvnNo")
    @Expose
    private Integer grnGvnNo;
    @SerializedName("spNo")
    @Expose
    private Integer spNo;
    @SerializedName("count")
    @Expose
    private Integer count;

    public Integer getFrSettingId() {
        return frSettingId;
    }

    public void setFrSettingId(Integer frSettingId) {
        this.frSettingId = frSettingId;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    public Integer getSellBillNo() {
        return sellBillNo;
    }

    public void setSellBillNo(Integer sellBillNo) {
        this.sellBillNo = sellBillNo;
    }

    public Integer getGrnGvnNo() {
        return grnGvnNo;
    }

    public void setGrnGvnNo(Integer grnGvnNo) {
        this.grnGvnNo = grnGvnNo;
    }

    public Integer getSpNo() {
        return spNo;
    }

    public void setSpNo(Integer spNo) {
        this.spNo = spNo;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "ChalanSettingValues{" +
                "frSettingId=" + frSettingId +
                ", frId=" + frId +
                ", frCode='" + frCode + '\'' +
                ", sellBillNo=" + sellBillNo +
                ", grnGvnNo=" + grnGvnNo +
                ", spNo=" + spNo +
                ", count=" + count +
                '}';
    }
}
