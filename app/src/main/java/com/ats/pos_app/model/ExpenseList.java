package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExpenseList {

    @SerializedName("expId")
    @Expose
    private Integer expId;
    @SerializedName("chalanNo")
    @Expose
    private String chalanNo;
    @SerializedName("expType")
    @Expose
    private String expType;
    @SerializedName("imgName")
    @Expose
    private String imgName;
    @SerializedName("chAmt")
    @Expose
    private String chAmt;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("remark")
    @Expose
    private String remark;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("expDate")
    @Expose
    private String expDate;
    @SerializedName("exInt1")
    @Expose
    private Integer exInt1;
    @SerializedName("exInt2")
    @Expose
    private Integer exInt2;
    @SerializedName("exInt3")
    @Expose
    private Integer exInt3;
    @SerializedName("exInt4")
    @Expose
    private Integer exInt4;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
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

    public ExpenseList(Integer expId, String chalanNo, String expType, String imgName, String chAmt, Integer frId, String remark, Integer status, String expDate, Integer exInt1, Integer exInt2, Integer exInt3, Integer exInt4, Integer delStatus, String exVar1, String exVar2, String exVar3, String exVar4) {
        this.expId = expId;
        this.chalanNo = chalanNo;
        this.expType = expType;
        this.imgName = imgName;
        this.chAmt = chAmt;
        this.frId = frId;
        this.remark = remark;
        this.status = status;
        this.expDate = expDate;
        this.exInt1 = exInt1;
        this.exInt2 = exInt2;
        this.exInt3 = exInt3;
        this.exInt4 = exInt4;
        this.delStatus = delStatus;
        this.exVar1 = exVar1;
        this.exVar2 = exVar2;
        this.exVar3 = exVar3;
        this.exVar4 = exVar4;
    }

    public Integer getExpId() {
        return expId;
    }

    public void setExpId(Integer expId) {
        this.expId = expId;
    }

    public String getChalanNo() {
        return chalanNo;
    }

    public void setChalanNo(String chalanNo) {
        this.chalanNo = chalanNo;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getChAmt() {
        return chAmt;
    }

    public void setChAmt(String chAmt) {
        this.chAmt = chAmt;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getExpDate() {
        return expDate;
    }

    public void setExpDate(String expDate) {
        this.expDate = expDate;
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

    public Integer getExInt4() {
        return exInt4;
    }

    public void setExInt4(Integer exInt4) {
        this.exInt4 = exInt4;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
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

    @Override
    public String toString() {
        return "ExpenseList{" +
                "expId=" + expId +
                ", chalanNo='" + chalanNo + '\'' +
                ", expType='" + expType + '\'' +
                ", imgName='" + imgName + '\'' +
                ", chAmt='" + chAmt + '\'' +
                ", frId=" + frId +
                ", remark='" + remark + '\'' +
                ", status=" + status +
                ", expDate='" + expDate + '\'' +
                ", exInt1=" + exInt1 +
                ", exInt2=" + exInt2 +
                ", exInt3=" + exInt3 +
                ", exInt4=" + exInt4 +
                ", delStatus=" + delStatus +
                ", exVar1='" + exVar1 + '\'' +
                ", exVar2='" + exVar2 + '\'' +
                ", exVar3='" + exVar3 + '\'' +
                ", exVar4='" + exVar4 + '\'' +
                '}';
    }
}
