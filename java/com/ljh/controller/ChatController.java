package com.ljh.controller;

import com.ljh.dao.ChatDao;
import com.ljh.pojo.Chat;
import com.ljh.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    private ChatService chatService;

    @RequestMapping("/getChat")
    @ResponseBody
    public List<Chat> getChat(int sendId) {
        return chatService.getChat(sendId);
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public List<Chat> getUser() {
        return chatService.getUser();
    }

    @RequestMapping("/recent")
    @ResponseBody
    public Chat getRecent(int sendId) {
        return chatService.Recent(sendId);
    }
}
