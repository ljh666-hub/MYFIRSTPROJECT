package com.ljh.controller;

import com.ljh.dao.ShoppingCarDao;
import com.ljh.exception.BusinessException;
import com.ljh.pojo.ShoppingCar;
import com.ljh.pojo.Users;
import com.ljh.service.ShoppingCarService;
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
@RequestMapping("/shoppingCar")
public class ShoppingCarController {
    @Autowired
    private ShoppingCarService shoppingCarService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShoppingCarDao shoppingCarDao;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/insert")
    @ResponseBody
    public Result insert(@RequestBody ShoppingCar shoppingCar, HttpSession session) {
            String userName = (String)session.getAttribute("userName");
            Users users = userService.selectByUserName(userName);
            shoppingCar.setUserId(users.getUserId());
        if(shoppingCarService.insert(shoppingCar)>0){
            redisTemplate.delete(redisTemplate.keys("car*"));
            return new Result(null,Status.CARADD_SUCCESS,"成功加入购物车");
        }else {
            throw new BusinessException("服务器繁忙，请稍后重试",Status.BUSINESS_ERROR);
        }
    }

    @RequestMapping("/selectAll")
    @ResponseBody
    public Result selectByUserId(int nowPage,HttpSession session) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        int userId = users.getUserId();
        int count = shoppingCarDao.count();
        String key = "car"+nowPage+userId;
        List<ShoppingCar> cars =(List<ShoppingCar>) redisTemplate.opsForValue().get(key);
        if (cars!=null&& !cars.isEmpty()){
            return new Result(cars,count,"");
        }

        cars = shoppingCarService.selectByUserId(userId,nowPage);
        redisTemplate.opsForValue().set(key,cars);
        return new Result(cars,count,"");
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Result delete(int carId) {
        if(shoppingCarService.delete(carId)){
            redisTemplate.delete(redisTemplate.keys("car*"));
            return new Result(null,Status.CARDELETE_SUCCESS,"删除成功");
        }else {
            throw new BusinessException("系统繁忙，请稍后重试", Status.BUSINESS_ERROR);
        }
    }

    @RequestMapping("/selectByBrandName")
    @ResponseBody
    public List<ShoppingCar> selectByBrandName(String brandName) {
        brandName = "%"+brandName+"%";
        String key = "car"+brandName;
        List<ShoppingCar> cars =(List<ShoppingCar>) redisTemplate.opsForValue().get(key);
        if (cars!=null&& !cars.isEmpty()){
            return cars;
        }

        cars = shoppingCarService.selectByBrandName(brandName);
        redisTemplate.opsForValue().set(key,cars);
        return cars;
    }

    @RequestMapping("/findFirst")
    @ResponseBody
    public List<ShoppingCar> findFirst(HttpSession session) {
        String userName = (String)session.getAttribute("userName");
        Users users = userService.selectByUserName(userName);
        int userId = users.getUserId();
        return shoppingCarDao.findFirst(userId);
    }
}
