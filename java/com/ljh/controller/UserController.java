package com.ljh.controller;

import com.ljh.DTO.CountRegisterDTO;
import com.ljh.pojo.Users;
import com.ljh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/addUser")
    @ResponseBody
    public Result addUser(@RequestBody Users user){
        Users exist = userService.selectByUserName(user.getUserName());
        if (exist == null){
            if(userService.insertUser(user)){
                redisTemplate.delete(redisTemplate.keys("user*"));
                return new Result(null,Status.USERADD_SUCCESS,"注册成功");
            }else {
                return new Result(null,Status.USERADD_ERROR,"注册失败");
            }
        }else {
            return new Result(null,Status.USERADD_ERROR,"用户名已存在，请换一个");
        }
    }

    @RequestMapping("/userPage")
    @ResponseBody
    public Result userPage(Integer nowPage){
        int i = userService.countUser();
        String key = "user"+nowPage;
        List<Users> users= (List<Users>)redisTemplate.opsForValue().get(key);
        if(users!=null&& !users.isEmpty()){
            return new Result(users,i,"ok");
        }

        users = userService.userPage(nowPage);
        redisTemplate.opsForValue().set(key,users);
        return new Result(users,i,"ok");
    }

    @RequestMapping("/updateUser")
    @ResponseBody
    public Result updateUser(String userStatus,int userId){
        if(userService.updateUser(userStatus,userId)){
            redisTemplate.delete(redisTemplate.keys("user*"));
            return new Result(null,Status.USERUPDATE_SUCCESS,"操作成功");
        }else {
            return new Result(null,Status.USERUPDATE_ERROR,"操作失败");
        }
    }

    @RequestMapping("/selectUser")
    @ResponseBody
    public Result selectUser(String userName, String userPassword, HttpSession session){
        Users user = userService.selectUser(userName, userPassword);
        if(user!=null && Objects.equals(user.getUserStatus(), "启用")){
            session.setAttribute("userName",userName);
            session.setMaxInactiveInterval(120*60);
            return new Result(session.getId(),Status.USERSELECT_SUCCESS,"登录成功");
        } else if (user!=null && Objects.equals(user.getUserStatus(), "封禁")) {
             return new Result(null,Status.USERSELECT_ERROR,"你的账号处于封禁中，请联系管理员");
        } else {
            return new Result(null, Status.USERSELECT_ERROR, "账号或密码不正确");
        }
    }

    @RequestMapping("/selectByUserName")
    @ResponseBody
    public Users selectByUserName(HttpSession session){
        String userName = (String) session.getAttribute("userName");
        return userService.selectByUserName(userName);
    }

    @RequestMapping("/selectById")
    @ResponseBody
    public Users selectById(int userId){
        return userService.selectByUserID(userId);
    }

    @RequestMapping("/clearUnread")
    @ResponseBody
    public void clearUnread(int userId){
       userService.clearUnread(userId);
    }


    @RequestMapping("/updateUserDetail")
    @ResponseBody
    public Result updateUserDetail(@RequestBody Users user,HttpSession session){
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        user.setUserId(users.getUserId());
        if (userService.selectByUserName(user.getUserName()) == null) {
            if (userService.updateUserDetail(user)){
                session.setAttribute("userName",user.getUserName());
                session.setMaxInactiveInterval(120*60);
                redisTemplate.delete(redisTemplate.keys("user*"));
                return new Result(null,Status.USERUPDATE_SUCCESS,"修改成功");
            }else {
                return new Result(null,Status.USERUPDATE_ERROR,"修改失败");
            }
        }else{
            return new Result(null,Status.USERUPDATE_ERROR,"用户名已存在，换一个试试");
        }
    }

    @RequestMapping("/CountRegister")
    @ResponseBody
    public CountRegisterDTO CountRegister(){
        LocalDate registerTime1 = LocalDate.now().plusMonths(-1).plusDays(-1);
        LocalDate registerTime2 = LocalDate.now().plusDays(1);
        List<Users> users = userService.CountRegister(registerTime1, registerTime2);
        List<Integer> num = new ArrayList<>();
        List<String> registerTime = new ArrayList<>();
        List<Integer> total = new ArrayList<>();
        int i = 0;
        while (!registerTime1.equals(registerTime2)) {
            registerTime1 = registerTime1.plusDays(1);
            if (!users.isEmpty()) {
                boolean isExist = false;
                for (Users user : users) {
                    if (user.getRegisterTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(registerTime1)) {
                        isExist = true;
                        i+=user.getNum();
                        total.add(i);
                        num.add(user.getNum());
                        registerTime.add(registerTime1.toString());
                        break;
                    }
                }
                if (!isExist) {
                    num.add(0);
                    total.add(i);
                    registerTime.add(registerTime1.toString());
                }

            }
        }
            CountRegisterDTO countRegisterDTO = new CountRegisterDTO();
            countRegisterDTO.setNum(num);
            countRegisterDTO.setRegisterTime(registerTime);
            countRegisterDTO.setTotal(total);
            return countRegisterDTO;

    }
}
