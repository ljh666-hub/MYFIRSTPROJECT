package com.ljh.controller;

import com.ljh.dao.AddressDao;
import com.ljh.pojo.Address;
import com.ljh.pojo.Users;
import com.ljh.service.AddressService;
import com.ljh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private UserService userService;
    @Autowired
    private AddressService addressService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/addAddress")
    @ResponseBody
    public Result addAddress(@RequestBody Address address){
        if(addressService.addAddress(address)){
            redisTemplate.delete("address*");
            return new Result(null,Status.ADDRESSADD_SUCCESS,"添加成功");
        }else {
            return new Result(null,Status.ADDRESSADD_ERROR,"添加失败");
        }
    }

    @RequestMapping("/findAddress")
    @ResponseBody
    public List<Address> findAddress(HttpSession session){
        String userName = (String) session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        int userId = users.getUserId();
        String key = "address"+userId;
        List<Address> addresses =(List<Address>) redisTemplate.opsForValue().get(key);
        if(addresses!=null&& !addresses.isEmpty()){
            return addresses;
        }

        addresses = addressService.findByUserId(userId);
        redisTemplate.opsForValue().set(key,addresses);
        return addresses;
    }

    @RequestMapping("/deleteAddress")
    @ResponseBody
    public Result findAddress(int addressId){
        if(addressService.deleteAddress(addressId)){
            redisTemplate.delete(redisTemplate.keys("address*"));
            return new Result(null,Status.ADDRESSDELETE_SUCCESS,"删除成功");
        }else {
            return new Result(null, Status.ADDRESSDELETE_ERROR, "删除失败");
        }
    }
}
