package com.ats.pos_app.model;

public class EmployeeList {
    private String EmployeeName;
    private String ContactNo;
    private String Address;
    private String JoiningDate;
    private int Code;

    public EmployeeList(String employeeName, String contactNo, String address, String joiningDate, int code) {
        EmployeeName = employeeName;
        ContactNo = contactNo;
        Address = address;
        JoiningDate = joiningDate;
        Code = code;
    }

    public String getEmployeeName() {
        return EmployeeName;
    }

    public void setEmployeeName(String employeeName) {
        EmployeeName = employeeName;
    }

    public String getContactNo() {
        return ContactNo;
    }

    public void setContactNo(String contactNo) {
        ContactNo = contactNo;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getJoiningDate() {
        return JoiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        JoiningDate = joiningDate;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    @Override
    public String toString() {
        return "EmployeeList{" +
                "EmployeeName='" + EmployeeName + '\'' +
                ", ContactNo='" + ContactNo + '\'' +
                ", Address='" + Address + '\'' +
                ", JoiningDate='" + JoiningDate + '\'' +
                ", Code=" + Code +
                '}';
    }
}
