package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplyment {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("frId")
    @Expose
    private Integer frId;
    @SerializedName("frPanNo")
    @Expose
    private String frPanNo;
    @SerializedName("frState")
    @Expose
    private String frState;
    @SerializedName("frCountry")
    @Expose
    private String frCountry;
    @SerializedName("pass1")
    @Expose
    private String pass1;
    @SerializedName("pass2")
    @Expose
    private String pass2;
    @SerializedName("pass3")
    @Expose
    private String pass3;
    @SerializedName("pass4")
    @Expose
    private String pass4;
    @SerializedName("pass5")
    @Expose
    private String pass5;
    @SerializedName("frequency")
    @Expose
    private Integer frequency;
    @SerializedName("pestControlDate")
    @Expose
    private String pestControlDate;
    @SerializedName("remainderDate")
    @Expose
    private String remainderDate;
    @SerializedName("delStatus")
    @Expose
    private Integer delStatus;
    @SerializedName("noInRoute")
    @Expose
    private Integer noInRoute;
    @SerializedName("isTallySync")
    @Expose
    private Integer isTallySync;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFrId() {
        return frId;
    }

    public void setFrId(Integer frId) {
        this.frId = frId;
    }

    public String getFrPanNo() {
        return frPanNo;
    }

    public void setFrPanNo(String frPanNo) {
        this.frPanNo = frPanNo;
    }

    public String getFrState() {
        return frState;
    }

    public void setFrState(String frState) {
        this.frState = frState;
    }

    public String getFrCountry() {
        return frCountry;
    }

    public void setFrCountry(String frCountry) {
        this.frCountry = frCountry;
    }

    public String getPass1() {
        return pass1;
    }

    public void setPass1(String pass1) {
        this.pass1 = pass1;
    }

    public String getPass2() {
        return pass2;
    }

    public void setPass2(String pass2) {
        this.pass2 = pass2;
    }

    public String getPass3() {
        return pass3;
    }

    public void setPass3(String pass3) {
        this.pass3 = pass3;
    }

    public String getPass4() {
        return pass4;
    }

    public void setPass4(String pass4) {
        this.pass4 = pass4;
    }

    public String getPass5() {
        return pass5;
    }

    public void setPass5(String pass5) {
        this.pass5 = pass5;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public String getPestControlDate() {
        return pestControlDate;
    }

    public void setPestControlDate(String pestControlDate) {
        this.pestControlDate = pestControlDate;
    }

    public String getRemainderDate() {
        return remainderDate;
    }

    public void setRemainderDate(String remainderDate) {
        this.remainderDate = remainderDate;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

    public Integer getNoInRoute() {
        return noInRoute;
    }

    public void setNoInRoute(Integer noInRoute) {
        this.noInRoute = noInRoute;
    }

    public Integer getIsTallySync() {
        return isTallySync;
    }

    public void setIsTallySync(Integer isTallySync) {
        this.isTallySync = isTallySync;
    }

    @Override
    public String toString() {
        return "Supplyment{" +
                "error=" + error +
                ", message='" + message + '\'' +
                ", id=" + id +
                ", frId=" + frId +
                ", frPanNo='" + frPanNo + '\'' +
                ", frState='" + frState + '\'' +
                ", frCountry='" + frCountry + '\'' +
                ", pass1='" + pass1 + '\'' +
                ", pass2='" + pass2 + '\'' +
                ", pass3='" + pass3 + '\'' +
                ", pass4='" + pass4 + '\'' +
                ", pass5='" + pass5 + '\'' +
                ", frequency=" + frequency +
                ", pestControlDate='" + pestControlDate + '\'' +
                ", remainderDate='" + remainderDate + '\'' +
                ", delStatus=" + delStatus +
                ", noInRoute=" + noInRoute +
                ", isTallySync=" + isTallySync +
                '}';
    }
}
