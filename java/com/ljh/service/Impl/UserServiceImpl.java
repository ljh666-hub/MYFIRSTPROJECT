package com.ljh.service.Impl;

import com.ljh.dao.UserDao;
import com.ljh.pojo.Users;
import com.ljh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public boolean insertUser(Users user) {
        int i = userDao.insertUser(user);
        return i>0;
    }

    @Override
    public int countUser() {
        return userDao.countUsers();
    }

    @Override
    public List<Users> userPage(Integer nowPage) {
        return userDao.userPage(nowPage);
    }

    @Override
    public boolean updateUser(String userStatus, int userId) {
        return userDao.updateUser(userStatus, userId) > 0;
    }

    @Override
    public Users selectUser(String userName, String userPassword) {
        Users users = userDao.selectUser(userName, userPassword);
        return users;
    }

    @Override
    public Users selectByUserID(int userId) {
        return userDao.selectByUserId(userId);
    }

    @Override
    public Users selectByUserName(String userName) {
        return userDao.selectByUserName(userName);
    }

    @Override
    public boolean clearUnread(int userId) {
        return userDao.clearUnread(userId)> 0 ;
    }

    @Override
    public List<Users> CountRegister(LocalDate registerTime1, LocalDate registerTime2) {
        return userDao.CountRegister(registerTime1, registerTime2);
    }

    @Override
    public boolean updateUserDetail(Users user) {
        return userDao.updateUserDetail(user)>0;
    }
}
