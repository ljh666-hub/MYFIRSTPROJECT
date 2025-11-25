package com.ljh.pojo;

import java.io.Serializable;

public class ShoppingCar implements Serializable {
    private  int carId;
    private int brandId;
    private String brandName;
    private String categoryName;
    private int amount;
    private int price;
    private int userId;
    private String imageUrl;

    @Override
    public String toString() {
        return "ShoppingCar{" +
                "carId=" + carId +
                ", brandId=" + brandId +
                ", brandName='" + brandName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", amount=" + amount +
                ", price=" + price +
                ", userId=" + userId +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
