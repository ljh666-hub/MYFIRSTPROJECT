package com.ljh.server;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSON;
import com.ljh.dao.ChatDao;
import com.ljh.dao.UserDao;
import com.ljh.pojo.Chat;
import com.ljh.pojo.Users;
import com.ljh.service.UserService;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.TransactionStatus;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;




@Component
@ServerEndpoint("/ws/{userId}")
public class WebSocketServer {
    private static UserService userService;
    private static ChatDao chatDao;
    private static Map<String, Session> sessions = new HashMap<>();
    public static TransactionDefinition transactionDefinition;
    public static UserDao userDao;


    @Autowired
   public void setUserService(UserService userService) {
        WebSocketServer.userService = userService;
    }
    @Autowired
    public void setChatDao(ChatDao chatDao) {
        WebSocketServer.chatDao = chatDao;
    }
    @Autowired
    public void setUserDao(UserDao userDao) {
        WebSocketServer.userDao = userDao;
    }



    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        sessions.put(userId, session);
    }

    @OnMessage
    public void onMessage(String message, Session session,@PathParam("userId") String sendId) {
        Map map = JSON.parseObject(message, Map.class);
        int receiveId = (int)map.get("receiveId");
        String content = (String)map.get("content");
        LocalDateTime sendTime = LocalDateTime.now();

        Session receiveSession = sessions.get(Integer.toString(receiveId));
        if (receiveSession != null && receiveSession.isOpen()) {
            String data = JSON.toJSONString(Map.of("sendId", sendId, "content", content, "sendTime", sendTime));
            receiveSession.getAsyncRemote().sendText(data);
        }

        if (Integer.parseInt(sendId) != 0) {
            userDao.updateUnread(Integer.parseInt(sendId));
        }

        Chat chat = new Chat();
        chat.setSendId(Integer.parseInt(sendId));
        chat.setReceiveId(receiveId);
        chat.setContent(content);
        chat.setSendTime(sendTime);

        chatDao.addChat(chat);
        System.out.println("okok");
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session.getId());
    }
}
