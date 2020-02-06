package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Franchisee {

    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("frName")
    @Expose
    private String frName;
    @SerializedName("frCode")
    @Expose
    private String frCode;
    @SerializedName("frOpeningDate")
    @Expose
    private String frOpeningDate;
    @SerializedName("frRate")
    @Expose
    private Integer frRate;
    @SerializedName("frImage")
    @Expose
    private String frImage;
    @SerializedName("frRouteId")
    @Expose
    private Integer frRouteId;
    @SerializedName("routeName")
    @Expose
    private String routeName;
    @SerializedName("frCity")
    @Expose
    private String frCity;
    @SerializedName("frKg1")
    @Expose
    private Integer frKg1;
    @SerializedName("frKg2")
    @Expose
    private Integer frKg2;
    @SerializedName("frKg3")
    @Expose
    private Integer frKg3;
    @SerializedName("frKg4")
    @Expose
    private Integer frKg4;
    @SerializedName("frEmail")
    @Expose
    private String frEmail;
    @SerializedName("frPassword")
    @Expose
    private String frPassword;
    @SerializedName("frMob")
    @Expose
    private String frMob;
    @SerializedName("frOwner")
    @Expose
    private String frOwner;
    @SerializedName("frRateCat")
    @Expose
    private Integer frRateCat;
    @SerializedName("grnTwo")
    @Expose
    private Integer grnTwo;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("frRmn1")
    @Expose
    private String frRmn1;
    @SerializedName("showItems")
    @Expose
    private String showItems;
    @SerializedName("notShowItems")
    @Expose
    private String notShowItems;
    @SerializedName("frOpening")
    @Expose
    private Integer frOpening;
    @SerializedName("frPasswordKey")
    @Expose
    private String frPasswordKey;
    @SerializedName("isSameDayApplicable")
    @Expose
    private Integer isSameDayApplicable;
    @SerializedName("ownerBirthDate")
    @Expose
    private String ownerBirthDate;
    @SerializedName("fbaLicenseDate")
    @Expose
    private String fbaLicenseDate;
    @SerializedName("frAgreementDate")
    @Expose
    private String frAgreementDate;
    @SerializedName("frGstType")
    @Expose
    private Integer frGstType;
    @SerializedName("frGstNo")
    @Expose
    private String frGstNo;
    @SerializedName("stockType")
    @Expose
    private Integer stockType;
    @SerializedName("frAddress")
    @Expose
    private String frAddress;
    @SerializedName("frTarget")
    @Expose
    private Integer frTarget;
    @SerializedName("isSameState")
    @Expose
    private Integer isSameState;

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getFrName() {
        return frName;
    }

    public void setFrName(String frName) {
        this.frName = frName;
    }

    public String getFrCode() {
        return frCode;
    }

    public void setFrCode(String frCode) {
        this.frCode = frCode;
    }

    public String getFrOpeningDate() {
        return frOpeningDate;
    }

    public void setFrOpeningDate(String frOpeningDate) {
        this.frOpeningDate = frOpeningDate;
    }

    public Integer getFrRate() {
        return frRate;
    }

    public void setFrRate(Integer frRate) {
        this.frRate = frRate;
    }

    public String getFrImage() {
        return frImage;
    }

    public void setFrImage(String frImage) {
        this.frImage = frImage;
    }

    public Integer getFrRouteId() {
        return frRouteId;
    }

    public void setFrRouteId(Integer frRouteId) {
        this.frRouteId = frRouteId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getFrCity() {
        return frCity;
    }

    public void setFrCity(String frCity) {
        this.frCity = frCity;
    }

    public Integer getFrKg1() {
        return frKg1;
    }

    public void setFrKg1(Integer frKg1) {
        this.frKg1 = frKg1;
    }

    public Integer getFrKg2() {
        return frKg2;
    }

    public void setFrKg2(Integer frKg2) {
        this.frKg2 = frKg2;
    }

    public Integer getFrKg3() {
        return frKg3;
    }

    public void setFrKg3(Integer frKg3) {
        this.frKg3 = frKg3;
    }

    public Integer getFrKg4() {
        return frKg4;
    }

    public void setFrKg4(Integer frKg4) {
        this.frKg4 = frKg4;
    }

    public String getFrEmail() {
        return frEmail;
    }

    public void setFrEmail(String frEmail) {
        this.frEmail = frEmail;
    }

    public String getFrPassword() {
        return frPassword;
    }

    public void setFrPassword(String frPassword) {
        this.frPassword = frPassword;
    }

    public String getFrMob() {
        return frMob;
    }

    public void setFrMob(String frMob) {
        this.frMob = frMob;
    }

    public String getFrOwner() {
        return frOwner;
    }

    public void setFrOwner(String frOwner) {
        this.frOwner = frOwner;
    }

    public Integer getFrRateCat() {
        return frRateCat;
    }

    public void setFrRateCat(Integer frRateCat) {
        this.frRateCat = frRateCat;
    }

    public Integer getGrnTwo() {
        return grnTwo;
    }

    public void setGrnTwo(Integer grnTwo) {
        this.grnTwo = grnTwo;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public String getFrRmn1() {
        return frRmn1;
    }

    public void setFrRmn1(String frRmn1) {
        this.frRmn1 = frRmn1;
    }

    public String getShowItems() {
        return showItems;
    }

    public void setShowItems(String showItems) {
        this.showItems = showItems;
    }

    public String getNotShowItems() {
        return notShowItems;
    }

    public void setNotShowItems(String notShowItems) {
        this.notShowItems = notShowItems;
    }

    public Integer getFrOpening() {
        return frOpening;
    }

    public void setFrOpening(Integer frOpening) {
        this.frOpening = frOpening;
    }

    public String getFrPasswordKey() {
        return frPasswordKey;
    }

    public void setFrPasswordKey(String frPasswordKey) {
        this.frPasswordKey = frPasswordKey;
    }

    public Integer getIsSameDayApplicable() {
        return isSameDayApplicable;
    }

    public void setIsSameDayApplicable(Integer isSameDayApplicable) {
        this.isSameDayApplicable = isSameDayApplicable;
    }

    public String getOwnerBirthDate() {
        return ownerBirthDate;
    }

    public void setOwnerBirthDate(String ownerBirthDate) {
        this.ownerBirthDate = ownerBirthDate;
    }

    public String getFbaLicenseDate() {
        return fbaLicenseDate;
    }

    public void setFbaLicenseDate(String fbaLicenseDate) {
        this.fbaLicenseDate = fbaLicenseDate;
    }

    public String getFrAgreementDate() {
        return frAgreementDate;
    }

    public void setFrAgreementDate(String frAgreementDate) {
        this.frAgreementDate = frAgreementDate;
    }

    public Integer getFrGstType() {
        return frGstType;
    }

    public void setFrGstType(Integer frGstType) {
        this.frGstType = frGstType;
    }

    public String getFrGstNo() {
        return frGstNo;
    }

    public void setFrGstNo(String frGstNo) {
        this.frGstNo = frGstNo;
    }

    public Integer getStockType() {
        return stockType;
    }

    public void setStockType(Integer stockType) {
        this.stockType = stockType;
    }

    public String getFrAddress() {
        return frAddress;
    }

    public void setFrAddress(String frAddress) {
        this.frAddress = frAddress;
    }

    public Integer getFrTarget() {
        return frTarget;
    }

    public void setFrTarget(Integer frTarget) {
        this.frTarget = frTarget;
    }

    public Integer getIsSameState() {
        return isSameState;
    }

    public void setIsSameState(Integer isSameState) {
        this.isSameState = isSameState;
    }

    @Override
    public String toString() {
        return "Franchisee{" +
                "frId=" + frId +
                ", frName='" + frName + '\'' +
                ", frCode='" + frCode + '\'' +
                ", frOpeningDate='" + frOpeningDate + '\'' +
                ", frRate=" + frRate +
                ", frImage='" + frImage + '\'' +
                ", frRouteId=" + frRouteId +
                ", routeName='" + routeName + '\'' +
                ", frCity='" + frCity + '\'' +
                ", frKg1=" + frKg1 +
                ", frKg2=" + frKg2 +
                ", frKg3=" + frKg3 +
                ", frKg4=" + frKg4 +
                ", frEmail='" + frEmail + '\'' +
                ", frPassword='" + frPassword + '\'' +
                ", frMob='" + frMob + '\'' +
                ", frOwner='" + frOwner + '\'' +
                ", frRateCat=" + frRateCat +
                ", grnTwo=" + grnTwo +
                ", delStatus=" + delStatus +
                ", frRmn1='" + frRmn1 + '\'' +
                ", showItems='" + showItems + '\'' +
                ", notShowItems='" + notShowItems + '\'' +
                ", frOpening=" + frOpening +
                ", frPasswordKey='" + frPasswordKey + '\'' +
                ", isSameDayApplicable=" + isSameDayApplicable +
                ", ownerBirthDate='" + ownerBirthDate + '\'' +
                ", fbaLicenseDate='" + fbaLicenseDate + '\'' +
                ", frAgreementDate='" + frAgreementDate + '\'' +
                ", frGstType=" + frGstType +
                ", frGstNo='" + frGstNo + '\'' +
                ", stockType=" + stockType +
                ", frAddress='" + frAddress + '\'' +
                ", frTarget=" + frTarget +
                ", isSameState=" + isSameState +
                '}';
    }
}
