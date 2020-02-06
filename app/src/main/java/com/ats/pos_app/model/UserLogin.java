package com.ats.pos_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserLogin {
    @SerializedName("loginInfo")
    @Expose
    private LoginInfo loginInfo;
    @SerializedName("frEmp")
    @Expose
    private FrEmp frEmp;
    @SerializedName("franchisee")
    @Expose
    private Franchisee franchisee;

    public LoginInfo getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(LoginInfo loginInfo) {
        this.loginInfo = loginInfo;
    }

    public FrEmp getFrEmp() {
        return frEmp;
    }

    public void setFrEmp(FrEmp frEmp) {
        this.frEmp = frEmp;
    }

    public Franchisee getFranchisee() {
        return franchisee;
    }

    public void setFranchisee(Franchisee franchisee) {
        this.franchisee = franchisee;
    }

    @Override
    public String toString() {
        return "UserLogin{" +
                "loginInfo=" + loginInfo +
                ", frEmp=" + frEmp +
                ", franchisee=" + franchisee +
                '}';
    }
}
