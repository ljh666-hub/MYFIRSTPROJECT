package com.ljh.pojo;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.LocalTime;

public class Cupon {
    private int cuponId;
    private String cuponType;
    private float discountValue;
    private int moneyLimit;
    private String typeLimit;
    private LocalDate userLimit;
    private LocalDate createTime;
    private LocalDate endTime;
    private String cuponStatus;
    private int userId;
    private LocalDate getTime;
    private String useStatus;
    private int cuponAmount;
    private int orderId;
    private String userLimitString;
    private String endTimeString;
    private LocalDate now;
    private LocalDate registerTime;

    @Override
    public String toString() {
        return "Cupon{" +
                "cuponId=" + cuponId +
                ", cuponType='" + cuponType + '\'' +
                ", discountValue=" + discountValue +
                ", moneyLimit=" + moneyLimit +
                ", typeLimit='" + typeLimit + '\'' +
                ", userLimit=" + userLimit +
                ", createTime=" + createTime +
                ", endTime=" + endTime +
                ", cuponStatus='" + cuponStatus + '\'' +
                ", userId=" + userId +
                ", getTime=" + getTime +
                ", useStatus='" + useStatus + '\'' +
                ", cuponAmount=" + cuponAmount +
                ", orderId=" + orderId +
                ", userLimitString='" + userLimitString + '\'' +
                ", endTimeString='" + endTimeString + '\'' +
                ", now=" + now +
                ", registerTime=" + registerTime +
                '}';
    }

    public int getCuponId() {
        return cuponId;
    }

    public void setCuponId(int cuponId) {
        this.cuponId = cuponId;
    }

    public String getCuponType() {
        return cuponType;
    }

    public void setCuponType(String cuponType) {
        this.cuponType = cuponType;
    }

    public float getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(float discountValue) {
        this.discountValue = discountValue;
    }

    public int getMoneyLimit() {
        return moneyLimit;
    }

    public void setMoneyLimit(int moneyLimit) {
        this.moneyLimit = moneyLimit;
    }

    public String getTypeLimit() {
        return typeLimit;
    }

    public void setTypeLimit(String typeLimit) {
        this.typeLimit = typeLimit;
    }

    public LocalDate getUserLimit() {
        return userLimit;
    }

    public void setUserLimit(LocalDate userLimit) {
        this.userLimit = userLimit;
    }

    public LocalDate getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDate createTime) {
        this.createTime = createTime;
    }

    public LocalDate getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }

    public String getCuponStatus() {
        return cuponStatus;
    }

    public void setCuponStatus(String cuponStatus) {
        this.cuponStatus = cuponStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getGetTime() {
        return getTime;
    }

    public void setGetTime(LocalDate getTime) {
        this.getTime = getTime;
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus;
    }

    public int getCuponAmount() {
        return cuponAmount;
    }

    public void setCuponAmount(int cuponAmount) {
        this.cuponAmount = cuponAmount;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserLimitString() {
        return userLimitString;
    }

    public void setUserLimitString(String userLimitString) {
        this.userLimitString = userLimitString;
    }

    public String getEndTimeString() {
        return endTimeString;
    }

    public void setEndTimeString(String endTimeString) {
        this.endTimeString = endTimeString;
    }

    public LocalDate getNow() {
        return now;
    }

    public void setNow(LocalDate now) {
        this.now = now;
    }

    public LocalDate getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(LocalDate registerTime) {
        this.registerTime = registerTime;
    }
}
