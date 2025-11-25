package com.ljh.service;

import com.ljh.pojo.Users;

import java.time.LocalDate;
import java.util.List;

public interface UserService {
    boolean insertUser(Users user);

    int countUser();

    List<Users> userPage(Integer nowPage);

    boolean updateUser(String userStatus,int userId);

    Users selectUser(String userName,String userPassword);

    Users selectByUserID(int userId);

    Users selectByUserName(String userName);

    boolean clearUnread(int userId);

    List<Users> CountRegister(LocalDate registerTime1, LocalDate registerTime2);

    boolean updateUserDetail(Users user);
}
