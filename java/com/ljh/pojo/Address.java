package com.ljh.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private int addressId;
    private String addressDetail;
    private String realName;
    private int userId;
    private String addressTag;

    @Override
    public String toString() {
        return "Address{" +
                "addressId=" + addressId +
                ", addressDetail='" + addressDetail + '\'' +
                ", realName='" + realName + '\'' +
                ", userId=" + userId +
                ", addressTag='" + addressTag + '\'' +
                '}';
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }
}
