package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bills {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;

    @SerializedName("nameUser")
    @Expose
    private String nameUser;

    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("voucher")
    @Expose
    private String voucher;
    @SerializedName("billdate")
    @Expose
    private String billdate;

    public bills(Integer id, Integer idUser, String nameUser, Integer idProduct, Integer price, Integer count, String status, String voucher, String billdate) {
        this.id = id;
        this.idUser = idUser;
        this.nameUser = nameUser;
        this.idProduct = idProduct;
        this.price = price;
        this.count = count;
        this.status = status;
        this.voucher = voucher;
        this.billdate = billdate;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }



}
