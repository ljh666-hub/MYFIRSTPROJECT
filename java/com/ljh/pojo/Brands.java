package com.ljh.pojo;

import java.io.Serializable;

public class Brands implements Serializable {
    private static final long serialVersionUID = 1L;

    private int brandId;
    private String brandName;
    private int brandAmount;
    private String brandType;
    private String brandDetail;
    private int brandSell;
    private double brandPrice;
    private String imageUrl;
    private String detailImageUrl;
    private int brandPriceBegin;
    private int brandPriceEnd;
    private String rate;

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getBrandAmount() {
        return brandAmount;
    }

    public void setBrandAmount(int brandAmount) {
        this.brandAmount = brandAmount;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandType() {
        return brandType;
    }

    public void setBrandType(String brandType) {
        this.brandType = brandType;
    }

    public String getBrandDetail() {
        return brandDetail;
    }

    public void setBrandDetail(String brandDetail) {
        this.brandDetail = brandDetail;
    }

    public double getBrandPrice() {
        return brandPrice;
    }

    public void setBrandPrice(double brandPrice) {
        this.brandPrice = brandPrice;
    }

    public int getBrandSell() {
        return brandSell;
    }

    public void setBrandSell(int brandSell) {
        this.brandSell = brandSell;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDetailImageUrl() {
        return detailImageUrl;
    }

    public void setDetailImageUrl(String detailImageUrl) {
        this.detailImageUrl = detailImageUrl;
    }

    public int getBrandPriceBegin() {
        return brandPriceBegin;
    }

    public void setBrandPriceBegin(int brandPriceBegin) {
        this.brandPriceBegin = brandPriceBegin;
    }

    public int getBrandPriceEnd() {
        return brandPriceEnd;
    }

    public void setBrandPriceEnd(int brandPriceEnd) {
        this.brandPriceEnd = brandPriceEnd;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
}