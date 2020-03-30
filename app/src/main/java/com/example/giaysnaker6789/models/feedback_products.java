package com.example.giaysnaker6789.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class feedback_products {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idProduct")
    @Expose
    private Integer idProduct;
    @SerializedName("idUser")
    @Expose
    private Integer idUser;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("feedDate")
    @Expose
    private String feedDate;

    public feedback_products(Integer id, Integer idProduct, Integer idUser, String content, String feedDate) {
        this.id = id;
        this.idProduct = idProduct;
        this.idUser = idUser;
        this.content = content;
        this.feedDate = feedDate;
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(String feedDate) {
        this.feedDate = feedDate;
    }


}
