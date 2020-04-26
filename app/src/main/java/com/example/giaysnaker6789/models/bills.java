package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bills {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("idBill")
    @Expose
    private Integer idBill;
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

    @SerializedName("nameproduct")
    @Expose
    private String nameproduct;
    @SerializedName("originproduct")
    @Expose
    private String originproduct;
    @SerializedName("imageproduct")
    @Expose
    private String imageproduct;

    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;


    public bills(Integer id, Integer idBill, Integer idProduct, Integer price, Integer count, String status, String voucher, String billdate) {
        this.id = id;
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.price = price;
        this.count = count;
        this.status = status;
        this.voucher = voucher;
        this.billdate = billdate;
    }

    public bills(Integer id, Integer idBill, Integer idProduct, Integer price, Integer count, String status, String voucher, String billdate, String nameproduct, String originproduct, String imageproduct, Object createdAt, Object updatedAt) {
        this.id = id;
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.price = price;
        this.count = count;
        this.status = status;
        this.voucher = voucher;
        this.billdate = billdate;
        this.nameproduct = nameproduct;
        this.originproduct = originproduct;
        this.imageproduct = imageproduct;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdBill() {
        return idBill;
    }

    public void setIdBill(Integer idBill) {
        this.idBill = idBill;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
    }


    public String getNameproduct() {
        return nameproduct;
    }

    public void setNameproduct(String nameproduct) {
        this.nameproduct = nameproduct;
    }

    public String getOriginproduct() {
        return originproduct;
    }

    public void setOriginproduct(String originproduct) {
        this.originproduct = originproduct;
    }

    public String getImageproduct() {
        return imageproduct;
    }

    public void setImageproduct(String imageproduct) {
        this.imageproduct = imageproduct;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public Object getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Object updatedAt) {
        this.updatedAt = updatedAt;
    }
}
