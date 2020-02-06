package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrEmp {

    @SerializedName("frEmpId")
    @Expose
    private Integer frEmpId;
    @SerializedName("frEmpName")
    @Expose
    private String frEmpName;
    @SerializedName("frEmpContact")
    @Expose
    private String frEmpContact;
    @SerializedName("frEmpAddress")
    @Expose
    private String frEmpAddress;
    @SerializedName("frEmpJoiningDate")
    @Expose
    private String frEmpJoiningDate;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("isActive")
    @Expose
    private Integer isActive;
    @SerializedName("totalLimit")
    @Expose
    private Integer totalLimit;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("currentBillAmt")
    @Expose
    private Integer currentBillAmt;
    @SerializedName("updateDatetime")
    @Expose
    private String updateDatetime;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("empCode")
    @Expose
    private String empCode;
    @SerializedName("designation")
    @Expose
    private Integer designation;
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

    public Integer getFrEmpId() {
        return frEmpId;
    }

    public void setFrEmpId(Integer frEmpId) {
        this.frEmpId = frEmpId;
    }

    public String getFrEmpName() {
        return frEmpName;
    }

    public void setFrEmpName(String frEmpName) {
        this.frEmpName = frEmpName;
    }

    public String getFrEmpContact() {
        return frEmpContact;
    }

    public void setFrEmpContact(String frEmpContact) {
        this.frEmpContact = frEmpContact;
    }

    public String getFrEmpAddress() {
        return frEmpAddress;
    }

    public void setFrEmpAddress(String frEmpAddress) {
        this.frEmpAddress = frEmpAddress;
    }

    public String getFrEmpJoiningDate() {
        return frEmpJoiningDate;
    }

    public void setFrEmpJoiningDate(String frEmpJoiningDate) {
        this.frEmpJoiningDate = frEmpJoiningDate;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getTotalLimit() {
        return totalLimit;
    }

    public void setTotalLimit(Integer totalLimit) {
        this.totalLimit = totalLimit;
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

    public Integer getCurrentBillAmt() {
        return currentBillAmt;
    }

    public void setCurrentBillAmt(Integer currentBillAmt) {
        this.currentBillAmt = currentBillAmt;
    }

    public String getUpdateDatetime() {
        return updateDatetime;
    }

    public void setUpdateDatetime(String updateDatetime) {
        this.updateDatetime = updateDatetime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public Integer getDesignation() {
        return designation;
    }

    public void setDesignation(Integer designation) {
        this.designation = designation;
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
        return "FrEmp{" +
                "frEmpId=" + frEmpId +
                ", frEmpName='" + frEmpName + '\'' +
                ", frEmpContact='" + frEmpContact + '\'' +
                ", frEmpAddress='" + frEmpAddress + '\'' +
                ", frEmpJoiningDate='" + frEmpJoiningDate + '\'' +
                ", frId=" + frId +
                ", delStatus=" + delStatus +
                ", isActive=" + isActive +
                ", totalLimit=" + totalLimit +
                ", fromDate='" + fromDate + '\'' +
                ", toDate='" + toDate + '\'' +
                ", currentBillAmt=" + currentBillAmt +
                ", updateDatetime='" + updateDatetime + '\'' +
                ", password='" + password + '\'' +
                ", empCode='" + empCode + '\'' +
                ", designation=" + designation +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                '}';
    }
}
