package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class banners {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;
    @SerializedName("image")
    @Expose
    private String image;

    public banners(Integer id, Integer idProduct, String image) {
        this.id = id;
        this.idProduct = idProduct;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
