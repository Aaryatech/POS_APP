package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PettyCashHandOverTransaction {

    @SerializedName("cashHandoverId")
    @Expose
    private Integer cashHandoverId;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("fromUserId")
    @Expose
    private Integer fromUserId;
    @SerializedName("fromUsername")
    @Expose
    private String fromUsername;
    @SerializedName("toUserId")
    @Expose
    private Integer toUserId;
    @SerializedName("toUsername")
    @Expose
    private String toUsername;
    @SerializedName("amtHandover")
    @Expose
    private Integer amtHandover;
    @SerializedName("actualAmtHandover")
    @Expose
    private Integer actualAmtHandover;
    @SerializedName("openingAmt")
    @Expose
    private Integer openingAmt;
    @SerializedName("sellingAmt")
    @Expose
    private Integer sellingAmt;
    @SerializedName("transactionDate")
    @Expose
    private String transactionDate;
    @SerializedName("closingDate")
    @Expose
    private String closingDate;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("updateDatetime")
    @Expose
    private String updateDatetime;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exInt3")
    @Expose
    private Integer exInt3;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;

    public PettyCashHandOverTransaction(Integer cashHandoverId, Integer frId, Integer fromUserId, String fromUsername, Integer toUserId, String toUsername, Integer amtHandover, Integer actualAmtHandover, Integer openingAmt, Integer sellingAmt, String transactionDate, String closingDate, Integer delStatus, String updateDatetime, Integer exInt1, Integer exInt2, Integer exInt3, String exVar1, String exVar2, String exVar3) {
        this.cashHandoverId = cashHandoverId;
        this.frId = frId;
        this.fromUserId = fromUserId;
        this.fromUsername = fromUsername;
        this.toUserId = toUserId;
        this.toUsername = toUsername;
        this.amtHandover = amtHandover;
        this.actualAmtHandover = actualAmtHandover;
        this.openingAmt = openingAmt;
        this.sellingAmt = sellingAmt;
        this.transactionDate = transactionDate;
        this.closingDate = closingDate;
        this.delStatus = delStatus;
        this.updateDatetime = updateDatetime;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
    }

    public Integer getCashHandoverId() {
        return cashHandoverId;
    }

    public void setCashHandoverId(Integer cashHandoverId) {
        this.cashHandoverId = cashHandoverId;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public Integer getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Integer fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getFromUsername() {
        return fromUsername;
    }

    public void setFromUsername(String fromUsername) {
        this.fromUsername = fromUsername;
    }

    public Integer getToUserId() {
        return toUserId;
    }

    public void setToUserId(Integer toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUsername() {
        return toUsername;
    }

    public void setToUsername(String toUsername) {
        this.toUsername = toUsername;
    }

    public Integer getAmtHandover() {
        return amtHandover;
    }

    public void setAmtHandover(Integer amtHandover) {
        this.amtHandover = amtHandover;
    }

    public Integer getActualAmtHandover() {
        return actualAmtHandover;
    }

    public void setActualAmtHandover(Integer actualAmtHandover) {
        this.actualAmtHandover = actualAmtHandover;
    }

    public Integer getOpeningAmt() {
        return openingAmt;
    }

    public void setOpeningAmt(Integer openingAmt) {
        this.openingAmt = openingAmt;
    }

    public Integer getSellingAmt() {
        return sellingAmt;
    }

    public void setSellingAmt(Integer sellingAmt) {
        this.sellingAmt = sellingAmt;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public Integer getExInt1() {
        return exInt1;
    }

    public void setExInt1(Integer exInt1) {
        this.exInt1 = exInt1;
    }

    public Integer getExInt2() {
        return exInt2;
    }

    public void setExInt2(Integer exInt2) {
        this.exInt2 = exInt2;
    }

    public Integer getExInt3() {
        return exInt3;
    }

    public void setExInt3(Integer exInt3) {
        this.exInt3 = exInt3;
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

    public String getExVar3() {
        return exVar3;
    }

    public void setExVar3(String exVar3) {
        this.exVar3 = exVar3;
    }

    @Override
    public String toString() {
        return "PettyCashHandOverTransaction{" +
                "cashHandoverId=" + cashHandoverId +
                ", frId=" + frId +
                ", fromUserId=" + fromUserId +
                ", fromUsername='" + fromUsername + '\'' +
                ", toUserId=" + toUserId +
                ", toUsername='" + toUsername + '\'' +
                ", amtHandover=" + amtHandover +
                ", actualAmtHandover=" + actualAmtHandover +
                ", openingAmt=" + openingAmt +
                ", sellingAmt=" + sellingAmt +
                ", transactionDate='" + transactionDate + '\'' +
                ", closingDate='" + closingDate + '\'' +
                ", delStatus=" + delStatus +
                ", updateDatetime='" + updateDatetime + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
