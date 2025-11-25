package com.ljh.service.Impl;

import com.ljh.dao.ChatDao;
import com.ljh.pojo.Chat;
import com.ljh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatDao chatDao;

    @Override
    public List<Chat> getChat(int sendId) {
        return chatDao.getChat(sendId);
    }

    @Override
    public List<Chat> getUser() {
        return chatDao.getUser();
    }

    @Override
    public Chat Recent(int sendId) {
        return chatDao.Recent(sendId);
    }
}
