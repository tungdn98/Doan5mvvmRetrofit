package com.example.giaysnaker6789.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class vouchers {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;
    @SerializedName("voucher")
    @Expose
    private String voucher;
    @SerializedName("promotion")
    @Expose
    private Integer promotion;
    @SerializedName("endDate")
    @Expose
    private String endDate;

    public vouchers(Integer id, Integer idProduct, String voucher, Integer promotion, String endDate) {
        this.id = id;
        this.idProduct = idProduct;
        this.voucher = voucher;
        this.promotion = promotion;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }



}
