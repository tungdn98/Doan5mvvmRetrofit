package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class bills {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("origin")
    @Expose
    private String origin;
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
    private Object voucher;
    @SerializedName("billdate")
    @Expose
    private String billdate;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private Object updatedAt;

    public bills() {
    }

    public bills(String name, String image, String origin, Integer id, Integer idBill, Integer idProduct, Integer price, Integer count, String status, Object voucher, String billdate) {
        this.name = name;
        this.image = image;
        this.origin = origin;
        this.id = id;
        this.idBill = idBill;
        this.idProduct = idProduct;
        this.price = price;
        this.count = count;
        this.status = status;
        this.voucher = voucher;
        this.billdate = billdate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
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

    public Object getVoucher() {
        return voucher;
    }

    public void setVoucher(Object voucher) {
        this.voucher = voucher;
    }

    public String getBilldate() {
        return billdate;
    }

    public void setBilldate(String billdate) {
        this.billdate = billdate;
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