package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
        import com.google.gson.annotations.SerializedName;

public class user1s {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("account")
    @Expose
    private String account;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("sex")
    @Expose
    private String sex;
    @SerializedName("fbName")
    @Expose
    private String fbName;
    @SerializedName("linkfb")
    @Expose
    private String linkfb;
    @SerializedName("imagefb")
    @Expose
    private String imagefb;
    @SerializedName("idfb")
    @Expose
    private String idfb;

    public user1s(Integer id, String account, String password, String email, String address, String phone, String name, String sex, String fbName, String linkfb, String imagefb, String idfb) {
        this.id = id;
        this.account = account;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.name = name;
        this.sex = sex;
        this.fbName = fbName;
        this.linkfb = linkfb;
        this.imagefb = imagefb;
        this.idfb = idfb;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }

    public String getLinkfb() {
        return linkfb;
    }

    public void setLinkfb(String linkfb) {
        this.linkfb = linkfb;
    }

    public String getImagefb() {
        return imagefb;
    }

    public void setImagefb(String imagefb) {
        this.imagefb = imagefb;
    }

    public String getIdfb() {
        return idfb;
    }

    public void setIdfb(String idfb) {
        this.idfb = idfb;
    }



}