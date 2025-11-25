package com.ljh.pojo;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable {
    private String orderId;
    private String orderStatus;
    private String orderPerson;
    private int userId;
    private int brandId;
    private int amount;
    private String orderAddress;
    private String orderRemark;
    private int orderPrice;
    private String CategoryName;
    private String imageUrl;
    private String brandName;
    private int brandSell;
    private int nowPage;
    private Date orderTime;

    @Override
    public String toString() {
        return "Orders{" +
                "orderId='" + orderId + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderPerson='" + orderPerson + '\'' +
                ", userId=" + userId +
                ", brandId=" + brandId +
                ", amount=" + amount +
                ", orderAddress='" + orderAddress + '\'' +
                ", orderRemark='" + orderRemark + '\'' +
                ", orderPrice=" + orderPrice +
                ", CategoryName='" + CategoryName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", brandName='" + brandName + '\'' +
                ", brandSell=" + brandSell +
                ", nowPage=" + nowPage +
                ", orderTime=" + orderTime +
                '}';
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderPerson() {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson) {
        this.orderPerson = orderPerson;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public void setOrderAddress(String orderAddress) {
        this.orderAddress = orderAddress;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public int getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(int orderPrice) {
        this.orderPrice = orderPrice;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public int getBrandSell() {
        return brandSell;
    }

    public void setBrandSell(int brandSell) {
        this.brandSell = brandSell;
    }

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }
}
