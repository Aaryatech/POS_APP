package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Company {

    @SerializedName("compId")
    @Expose
    private Integer compId;
    @SerializedName("compName")
    @Expose
    private String compName;
    @SerializedName("factAddress")
    @Expose
    private String factAddress;
    @SerializedName("phoneNo1")
    @Expose
    private String phoneNo1;
    @SerializedName("phoneNo2")
    @Expose
    private String phoneNo2;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("gstin")
    @Expose
    private String gstin;
    @SerializedName("stateCode")
    @Expose
    private Integer stateCode;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("cinNo")
    @Expose
    private String cinNo;
    @SerializedName("fdaDeclaration")
    @Expose
    private String fdaDeclaration;
    @SerializedName("fdaLicenceNo")
    @Expose
    private String fdaLicenceNo;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("panNo")
    @Expose
    private String panNo;
    @SerializedName("exVar1")
    @Expose
    private String exVar1;
    @SerializedName("exVar2")
    @Expose
    private String exVar2;
    @SerializedName("exVar3")
    @Expose
    private String exVar3;
    @SerializedName("exVar4")
    @Expose
    private String exVar4;
    @SerializedName("exVar5")
    @Expose
    private String exVar5;
    @SerializedName("exVar6")
    @Expose
    private String exVar6;
    @SerializedName("delChalanPrefix")
    @Expose
    private String delChalanPrefix;
    @SerializedName("fromPinCode")
    @Expose
    private Integer fromPinCode;

    public Integer getCompId() {
        return compId;
    }

    public void setCompId(Integer compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getFactAddress() {
        return factAddress;
    }

    public void setFactAddress(String factAddress) {
        this.factAddress = factAddress;
    }

    public String getPhoneNo1() {
        return phoneNo1;
    }

    public void setPhoneNo1(String phoneNo1) {
        this.phoneNo1 = phoneNo1;
    }

    public String getPhoneNo2() {
        return phoneNo2;
    }

    public void setPhoneNo2(String phoneNo2) {
        this.phoneNo2 = phoneNo2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin = gstin;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCinNo() {
        return cinNo;
    }

    public void setCinNo(String cinNo) {
        this.cinNo = cinNo;
    }

    public String getFdaDeclaration() {
        return fdaDeclaration;
    }

    public void setFdaDeclaration(String fdaDeclaration) {
        this.fdaDeclaration = fdaDeclaration;
    }

    public String getFdaLicenceNo() {
        return fdaLicenceNo;
    }

    public void setFdaLicenceNo(String fdaLicenceNo) {
        this.fdaLicenceNo = fdaLicenceNo;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
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

    public String getExVar4() {
        return exVar4;
    }

    public void setExVar4(String exVar4) {
        this.exVar4 = exVar4;
    }

    public String getExVar5() {
        return exVar5;
    }

    public void setExVar5(String exVar5) {
        this.exVar5 = exVar5;
    }

    public String getExVar6() {
        return exVar6;
    }

    public void setExVar6(String exVar6) {
        this.exVar6 = exVar6;
    }

    public String getDelChalanPrefix() {
        return delChalanPrefix;
    }

    public void setDelChalanPrefix(String delChalanPrefix) {
        this.delChalanPrefix = delChalanPrefix;
    }

    public Integer getFromPinCode() {
        return fromPinCode;
    }

    public void setFromPinCode(Integer fromPinCode) {
        this.fromPinCode = fromPinCode;
    }

    @Override
    public String toString() {
        return "Company{" +
                "compId=" + compId +
                ", compName='" + compName + '\'' +
                ", factAddress='" + factAddress + '\'' +
                ", phoneNo1='" + phoneNo1 + '\'' +
                ", phoneNo2='" + phoneNo2 + '\'' +
                ", email='" + email + '\'' +
                ", gstin='" + gstin + '\'' +
                ", stateCode=" + stateCode +
                ", state='" + state + '\'' +
                ", cinNo='" + cinNo + '\'' +
                ", fdaDeclaration='" + fdaDeclaration + '\'' +
                ", fdaLicenceNo='" + fdaLicenceNo + '\'' +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", delStatus=" + delStatus +
                ", panNo='" + panNo + '\'' +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                ", exVar4='" + exVar4 + '\'' +
                ", exVar5='" + exVar5 + '\'' +
                ", exVar6='" + exVar6 + '\'' +
                ", delChalanPrefix='" + delChalanPrefix + '\'' +
                ", fromPinCode=" + fromPinCode +
                '}';
    }
}
