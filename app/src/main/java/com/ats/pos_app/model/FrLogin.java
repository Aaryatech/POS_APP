package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FrLogin {

    @SerializedName("loginInfo")
    @Expose
    private LoginInfo loginInfo;
    @SerializedName("franchisee")
    @Expose
    private Franchisee franchisee;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public Franchisee getFranchisee() {
        return franchisee;
    }

    public void setFranchisee(Franchisee franchisee) {
        this.franchisee = franchisee;
    }

    @Override
    public String toString() {
        return "FrLogin{" +
                "loginInfo=" + loginInfo +
                ", franchisee=" + franchisee +
                '}';
    }
}
