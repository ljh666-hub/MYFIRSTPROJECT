package com.ljh.service;

import com.ljh.pojo.Chat;

import java.util.List;

public interface ChatService {
    List<Chat> getChat(int sendId);

    List<Chat> getUser();

    Chat Recent(int sendId);
}
