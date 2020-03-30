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
    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("billdate")
    @Expose
    private String billdate;

    public bills(Integer id, Integer idUser, Integer idProduct, Integer count, String status, String billdate) {
        this.id = id;
        this.idUser = idUser;
        this.idProduct = idProduct;
        this.count = count;
        this.status = status;
        this.billdate = billdate;
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
