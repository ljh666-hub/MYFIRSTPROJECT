package com.ljh.pojo;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private int commentId;
    private int userId;
    private String CommentDetail;
    private int brandId;
    private int rate;
    private String stringCommentTime;
    private Date commentTime;
    private String userName;
    private String imageUrl;
    private String RateName;
    private String brandName;

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", CommentDetail='" + CommentDetail + '\'' +
                ", brandId=" + brandId +
                ", rate=" + rate +
                ", stringCommentTime='" + stringCommentTime + '\'' +
                ", commentTime=" + commentTime +
                ", userName='" + userName + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", RateName='" + RateName + '\'' +
                ", brandName='" + brandName + '\'' +
                '}';
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCommentDetail() {
        return CommentDetail;
    }

    public void setCommentDetail(String commentDetail) {
        CommentDetail = commentDetail;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public String getStringCommentTime() {
        return stringCommentTime;
    }

    public void setStringCommentTime(String stringCommentTime) {
        this.stringCommentTime = stringCommentTime;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRateName() {
        return RateName;
    }

    public void setRateName(String rateName) {
        RateName = rateName;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
