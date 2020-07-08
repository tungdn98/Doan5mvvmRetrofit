package com.example.giaysnaker6789.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

public class products {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("idProductType")
    @Expose
    private Integer idProductType;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("origin")
    @Expose
    private String origin;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("describe")
    @Expose
    private String describe;
    @SerializedName("price")
    @Expose
    private Integer price;
    @SerializedName("promotion")
    @Expose
    private Integer promotion;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("amount")
    @Expose
    private Integer amount;

    @SerializedName("saled")
    @Expose
    private Integer saled;

    @SerializedName("rate")
    @Expose
    private Float rate;


    public products() {
    }

    public products(Integer id, Integer idProductType, String name, String origin, String color, String weight, String describe, Integer price, Integer promotion, String image, Integer amount, Float rate) {
        this.id = id;
        this.idProductType = idProductType;
        this.name = name;
        this.origin = origin;
        this.color = color;
        this.weight = weight;
        this.describe = describe;
        this.price = price;
        this.promotion = promotion;
        this.image = image;
        this.amount = amount;
        this.rate = rate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdProductType() {
        return idProductType;
    }

    public void setIdProductType(Integer idProductType) {
        this.idProductType = idProductType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getPromotion() {
        return promotion;
    }

    public void setPromotion(Integer promotion) {
        this.promotion = promotion;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Integer getSaled() {
        return saled;
    }

    public void setSaled(Integer saled) {
        this.saled = saled;
    }
}

