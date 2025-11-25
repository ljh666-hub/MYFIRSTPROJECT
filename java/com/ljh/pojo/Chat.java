package com.ljh.pojo;

import java.time.LocalDateTime;

public class Chat {
    private int chatId;
    private int sendId;
    private int receiveId;
    private String content;
    private LocalDateTime sendTime;

    @Override
    public String toString() {
        return "Chat{" +
                "chatId=" + chatId +
                ", sendId=" + sendId +
                ", receiveId=" + receiveId +
                ", content='" + content + '\'' +
                ", sendTime=" + sendTime +
                '}';
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public int getSendId() {
        return sendId;
    }

    public void setSendId(int sendId) {
        this.sendId = sendId;
    }

    public int getReceiveId() {
        return receiveId;
    }

    public void setReceiveId(int receiveId) {
        this.receiveId = receiveId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getSendTime() {
        return sendTime;
    }

    public void setSendTime(LocalDateTime sendTime) {
        this.sendTime = sendTime;
    }
}
