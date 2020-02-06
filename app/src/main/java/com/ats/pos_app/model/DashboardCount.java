package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DashboardCount {

    @SerializedName("saleAmt")
    @Expose
    private Integer saleAmt;
    @SerializedName("purchaseAmt")
    @Expose
    private Float purchaseAmt;
    @SerializedName("discountAmt")
    @Expose
    private Float discountAmt;
    @SerializedName("creditAmt")
    @Expose
    private Integer creditAmt;
    @SerializedName("expenseAmt")
    @Expose
    private Integer expenseAmt;
    @SerializedName("advanceAmt")
    @Expose
    private Integer advanceAmt;
    @SerializedName("profitAmt")
    @Expose
    private Integer profitAmt;
    @SerializedName("cashAmt")
    @Expose
    private Float cashAmt;
    @SerializedName("cardAmt")
    @Expose
    private Integer cardAmt;
    @SerializedName("epayAmt")
    @Expose
    private Integer epayAmt;
    @SerializedName("noOfBillGenerated")
    @Expose
    private Integer noOfBillGenerated;
    @SerializedName("dailyMartList")
    @Expose
    private List<DailyMartList> dailyMartList = null;
    @SerializedName("advOrderList")
    @Expose
    private List<AdvOrderList> advOrderList = null;

    public Integer getSaleAmt() {
        return saleAmt;
    }

    public void setSaleAmt(Integer saleAmt) {
        this.saleAmt = saleAmt;
    }

    public Float getPurchaseAmt() {
        return purchaseAmt;
    }

    public void setPurchaseAmt(Float purchaseAmt) {
        this.purchaseAmt = purchaseAmt;
    }

    public Float getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(Float discountAmt) {
        this.discountAmt = discountAmt;
    }

    public Integer getCreditAmt() {
        return creditAmt;
    }

    public void setCreditAmt(Integer creditAmt) {
        this.creditAmt = creditAmt;
    }

    public Integer getExpenseAmt() {
        return expenseAmt;
    }

    public void setExpenseAmt(Integer expenseAmt) {
        this.expenseAmt = expenseAmt;
    }

    public Integer getAdvanceAmt() {
        return advanceAmt;
    }

    public void setAdvanceAmt(Integer advanceAmt) {
        this.advanceAmt = advanceAmt;
    }

    public Integer getProfitAmt() {
        return profitAmt;
    }

    public void setProfitAmt(Integer profitAmt) {
        this.profitAmt = profitAmt;
    }

    public Float getCashAmt() {
        return cashAmt;
    }

    public void setCashAmt(Float cashAmt) {
        this.cashAmt = cashAmt;
    }

    public Integer getCardAmt() {
        return cardAmt;
    }

    public void setCardAmt(Integer cardAmt) {
        this.cardAmt = cardAmt;
    }

    public Integer getEpayAmt() {
        return epayAmt;
    }

    public void setEpayAmt(Integer epayAmt) {
        this.epayAmt = epayAmt;
    }

    public Integer getNoOfBillGenerated() {
        return noOfBillGenerated;
    }

    public void setNoOfBillGenerated(Integer noOfBillGenerated) {
        this.noOfBillGenerated = noOfBillGenerated;
    }

    public List<DailyMartList> getDailyMartList() {
        return dailyMartList;
    }

    public void setDailyMartList(List<DailyMartList> dailyMartList) {
        this.dailyMartList = dailyMartList;
    }

    public List<AdvOrderList> getAdvOrderList() {
        return advOrderList;
    }

    public void setAdvOrderList(List<AdvOrderList> advOrderList) {
        this.advOrderList = advOrderList;
    }

    @Override
    public String toString() {
        return "DashboardCount{" +
                "saleAmt=" + saleAmt +
                ", purchaseAmt=" + purchaseAmt +
                ", discountAmt=" + discountAmt +
                ", creditAmt=" + creditAmt +
                ", expenseAmt=" + expenseAmt +
                ", advanceAmt=" + advanceAmt +
                ", profitAmt=" + profitAmt +
                ", cashAmt=" + cashAmt +
                ", cardAmt=" + cardAmt +
                ", epayAmt=" + epayAmt +
                ", noOfBillGenerated=" + noOfBillGenerated +
                ", dailyMartList=" + dailyMartList +
                ", advOrderList=" + advOrderList +
                '}';
    }
}
