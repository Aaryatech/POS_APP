package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PettyCashDetail {

    @SerializedName("pettycashId")
    @Expose
    private Integer pettycashId;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("date")
    @Expose
    private String  date;
    @SerializedName("openingAmt")
    @Expose
    private Integer openingAmt;
    @SerializedName("cashAmt")
    @Expose
    private Integer cashAmt;
    @SerializedName("cardAmt")
    @Expose
    private Integer cardAmt;
    @SerializedName("otherAmt")
    @Expose
    private Integer otherAmt;
    @SerializedName("totalAmt")
    @Expose
    private Integer totalAmt;
    @SerializedName("withdrawalAmt")
    @Expose
    private Integer withdrawalAmt;
    @SerializedName("closingAmt")
    @Expose
    private Integer closingAmt;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("opnEditAmt")
    @Expose
    private Integer opnEditAmt;
    @SerializedName("cashEditAmt")
    @Expose
    private Integer cashEditAmt;
    @SerializedName("cardEditAmt")
    @Expose
    private Integer cardEditAmt;
    @SerializedName("ttlEditAmt")
    @Expose
    private Integer ttlEditAmt;
    @SerializedName("exFloat1")
    @Expose
    private Integer exFloat1;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;

    public PettyCashDetail(Integer pettycashId, Integer frId, String date, Integer openingAmt, Integer cashAmt, Integer cardAmt, Integer otherAmt, Integer totalAmt, Integer withdrawalAmt, Integer closingAmt, Integer status, Integer opnEditAmt, Integer cashEditAmt, Integer cardEditAmt, Integer ttlEditAmt, Integer exFloat1, Integer exInt1, String exVar1, String exVar2) {
        this.pettycashId = pettycashId;
        this.frId = frId;
        this.date = date;
        this.openingAmt = openingAmt;
        this.cashAmt = cashAmt;
        this.cardAmt = cardAmt;
        this.otherAmt = otherAmt;
        this.totalAmt = totalAmt;
        this.withdrawalAmt = withdrawalAmt;
        this.closingAmt = closingAmt;
        this.status = status;
        this.opnEditAmt = opnEditAmt;
        this.cashEditAmt = cashEditAmt;
        this.cardEditAmt = cardEditAmt;
        this.ttlEditAmt = ttlEditAmt;
        this.exFloat1 = exFloat1;
        this.exInt1 = exInt1;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
    }

    public Integer getPettycashId() {
        return pettycashId;
    }

    public void setPettycashId(Integer pettycashId) {
        this.pettycashId = pettycashId;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getOpeningAmt() {
        return openingAmt;
    }

    public void setOpeningAmt(Integer openingAmt) {
        this.openingAmt = openingAmt;
    }

    public Integer getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(Integer cashAmt) {
        this.cashAmt = cashAmt;
    }

    public Integer getCardAmt() {
        return cardAmt;
    }

    public void setCardAmt(Integer cardAmt) {
        this.cardAmt = cardAmt;
    }

    public Integer getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(Integer otherAmt) {
        this.otherAmt = otherAmt;
    }

    public Integer getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(Integer totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Integer getWithdrawalAmt() {
        return withdrawalAmt;
    }

    public void setWithdrawalAmt(Integer withdrawalAmt) {
        this.withdrawalAmt = withdrawalAmt;
    }

    public Integer getClosingAmt() {
        return closingAmt;
    }

    public void setClosingAmt(Integer closingAmt) {
        this.closingAmt = closingAmt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getOpnEditAmt() {
        return opnEditAmt;
    }

    public void setOpnEditAmt(Integer opnEditAmt) {
        this.opnEditAmt = opnEditAmt;
    }

    public Integer getCashEditAmt() {
        return cashEditAmt;
    }

    public void setCashEditAmt(Integer cashEditAmt) {
        this.cashEditAmt = cashEditAmt;
    }

    public Integer getCardEditAmt() {
        return cardEditAmt;
    }

    public void setCardEditAmt(Integer cardEditAmt) {
        this.cardEditAmt = cardEditAmt;
    }

    public Integer getTtlEditAmt() {
        return ttlEditAmt;
    }

    public void setTtlEditAmt(Integer ttlEditAmt) {
        this.ttlEditAmt = ttlEditAmt;
    }

    public Integer getExFloat1() {
        return exFloat1;
    }

    public void setExFloat1(Integer exFloat1) {
        this.exFloat1 = exFloat1;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public String getExVar1() {
        return exVar1;
    }

    public void setExVar1(String exVar1) {
        this.exVar1 = exVar1;
    }

    public String getExVar2() {
        return exVar2;
    }

    public void setExVar2(String exVar2) {
        this.exVar2 = exVar2;
    }

    @Override
    public String toString() {
        return "PettyCashDetail{" +
                "pettycashId=" + pettycashId +
                ", frId=" + frId +
                ", date=" + date +
                ", openingAmt=" + openingAmt +
                ", cashAmt=" + cashAmt +
                ", cardAmt=" + cardAmt +
                ", otherAmt=" + otherAmt +
                ", totalAmt=" + totalAmt +
                ", withdrawalAmt=" + withdrawalAmt +
                ", closingAmt=" + closingAmt +
                ", status=" + status +
                ", opnEditAmt=" + opnEditAmt +
                ", cashEditAmt=" + cashEditAmt +
                ", cardEditAmt=" + cardEditAmt +
                ", ttlEditAmt=" + ttlEditAmt +
                ", exFloat1=" + exFloat1 +
                ", exInt1=" + exInt1 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                '}';
    }
}
