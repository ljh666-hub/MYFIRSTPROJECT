package com.ljh.controller;

import com.ljh.DTO.CountTotalDTO;
import com.ljh.DTO.FindUncancelDTO;
import com.ljh.dao.OrderDao;
import com.ljh.exception.BusinessException;
import com.ljh.pojo.Brands;
import com.ljh.pojo.Orders;
import com.ljh.pojo.Users;
import com.ljh.service.BrandService;
import com.ljh.service.CuponService;
import com.ljh.service.OrderService;
import com.ljh.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private CuponService cuponService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @RequestMapping("/addOrder")
    @ResponseBody
    @Transactional
    public Result insertOrder(@RequestBody Orders orders) {
        if(orderService.insertOrder(orders)){
            Brands brand = brandService.findBrandById(orders.getBrandId());
            int amount = brand.getBrandAmount()-orders.getAmount();
            int sell = brand.getBrandSell()+orders.getAmount();
                brandService.updateBrandByBrandId(amount,sell,orders.getBrandId());
                redisTemplate.delete(redisTemplate.keys("orders*"));
                redisTemplate.delete(redisTemplate.keys("brand*"));
                return new Result(null,Status.ORDERADD_SUCCESS,"ok");
        }else {
            throw new BusinessException("支付失败", Status.ORDERADD_ERROR);
        }
    }

    @RequestMapping("/countOrders")
    @ResponseBody
    public int countOrders() {
        return orderDao.countOrders();
    }

    @RequestMapping("/findOrder")
    @ResponseBody
    public Result findOrder(HttpSession session,String brandName,String orderStatus) {
        brandName = "%" +brandName+"%";
        String userName = (String)session.getAttribute("userName");
        Users user = userService.selectByUserName(userName);
        String key  = "orders"+userName+brandName+orderStatus;
        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return new Result(order,Status.ORDERFIND_SUCCESS,"");
        }

         order = orderService.findByBrandId(user.getUserId(),brandName,orderStatus);
         redisTemplate.opsForValue().set(key,order);
        return new Result(order,Status.ORDERFIND_SUCCESS,"");
    }

    @RequestMapping("/updateAddress")
    @ResponseBody
    public Result updateAddress(@RequestBody Orders orders) {
        if(orderService.updateOrder(orders)){
            redisTemplate.delete(redisTemplate.keys("orders*"));
            return new Result(null,Status.ORDERUPDATE_SUCCESS,"修改成功");
        }else{
            throw new BusinessException("修改失败，请稍后再试", Status.ORDERUPDATE_ERROR);
        }
    }

    @RequestMapping("/findAll")
    @ResponseBody
    public List<Orders> findAll() {
        String key = "orders1";
        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return order;
        }

        order = orderDao.findAll();
        redisTemplate.opsForValue().set(key,order);
        return order;
    }

    @RequestMapping("/findByOrderId")
    @ResponseBody
    public List<Orders> findByOrderId(String orderId,String brandName) {
        brandName = "%"+brandName+"%";
        String key = "orders"+orderId+"*"+brandName;
        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return order;
        }

        order =  orderService.findByOrderId(orderId,brandName);
        redisTemplate.opsForValue().set(key,order);
        return order;
    }

    @RequestMapping("/findByCondition")
    @ResponseBody
    public List<Orders> findByCondition(@RequestBody Orders orders) {
        redisTemplate.delete(redisTemplate.keys("orders*"));
        String key  = "orders"+orders.getOrderId()+"*"+orders.getOrderStatus();

        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return order;
        }

        order = orderDao.findByCondition(orders);
        redisTemplate.opsForValue().set(key,order);
        return order;
    }

    @RequestMapping("/deleteById")
    @ResponseBody
    public Result deleteById(String orderId) {
        if(orderService.deleteByOrderId(orderId)){
            redisTemplate.delete(redisTemplate.keys("orders*"));
            return new Result(null,Status.ORDERDELETE_SUCCESS,"删除成功");
        }else {
            return new Result(null,Status.ORDERDELETE_ERROR,"删除失败");
        }
    }

    @RequestMapping("/orderPage")
    @ResponseBody
    public Result orderPage(int nowPage) {
        String key  = "orders"+nowPage;
        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return new Result(order,orderService.countOrders(),"ok");
        }

        order = orderService.orderPage(nowPage);
        redisTemplate.opsForValue().set(key,order);
        return new Result(order,orderService.countOrders(),"ok");
    }

    @RequestMapping("/findByClient")
    @ResponseBody
    public List<Orders> findByClient(String orderTimeBegin, String orderTimeEnd, String brandType,String orderStatus) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime orderTime1 = LocalDateTime.parse(orderTimeBegin,dtf);
        LocalDateTime orderTime2 = LocalDateTime.parse(orderTimeEnd,dtf);
        String key = "orders"+orderTime1+"*"+orderTime2+"*"+brandType+"*"+orderStatus;
        List<Orders> order=(List<Orders>) redisTemplate.opsForValue().get(key);
        if(order!=null && !order.isEmpty()){
            return order;
        }

        order = orderDao.findByClient(orderTime1,orderTime2,brandType,orderStatus);
        redisTemplate.opsForValue().set(key,order);
        return order;
    }


    @RequestMapping("/CountTotal")
    @ResponseBody
    public CountTotalDTO CountTotal() {
        LocalDate orderTime2 = LocalDate.now();
        LocalDate orderTime1 = LocalDate.now().plusMonths(-1).plusDays(-1);
        List<Orders> orders = orderService.CountTotal(orderTime1, orderTime2);
        List<Integer> money = new ArrayList<>();
        List<String> date = new ArrayList<>();
        while (!orderTime1.equals(orderTime2)) {
            orderTime1 = orderTime1.plusDays(1);
            if (!orders.isEmpty()) {
                boolean isExist = false;
                for (Orders order : orders) {
                    if (order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(orderTime1)) {
                        money.add(order.getOrderPrice());
                        date.add(orderTime1.toString());
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    money.add(0);
                    date.add(orderTime1.toString());
                }
            }
        }
        CountTotalDTO countTotalDTO = new CountTotalDTO();
        countTotalDTO.setMoney(money);
        countTotalDTO.setDate(date);
        return countTotalDTO;
    }

    @RequestMapping("/findUncancel")
    @ResponseBody
    public FindUncancelDTO findUncancel() {
        LocalDate orderTime2 = LocalDate.now();
        LocalDate orderTime1 = LocalDate.now().plusMonths(-1).plusDays(-1);
        List<Orders> cancel = orderService.findCancel(orderTime1, orderTime2);
        List<Integer> orderAmount = new ArrayList<>();
        List<String> date = new ArrayList<>();
        List<Integer> allOrders = new ArrayList<>();
        int amount = 0;
        while (!orderTime1.equals(orderTime2)) {
            orderTime1 = orderTime1.plusDays(1);
            if (!cancel.isEmpty()) {
                boolean isExist = false;
                for (Orders order : cancel) {
                    if (order.getOrderTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(orderTime1)) {
                        int all = orderDao.findAmount(LocalDate.now().plusMonths(-1).plusDays(-1),orderTime1);
                        allOrders.add(all);
                        amount += order.getAmount();
                        int i = all-amount;
                        orderAmount.add(i);
                        date.add(orderTime1.toString());
                        isExist = true;
                        break;
                    }
                }
                if (!isExist) {
                    int all = orderDao.findAmount(LocalDate.now().plusMonths(-1).plusDays(-1),orderTime1);
                    allOrders.add(all);
                    orderAmount.add(all-amount);
                    date.add(orderTime1.toString());
                }
            }
        }
        FindUncancelDTO findUncancelDTO = new FindUncancelDTO();
        findUncancelDTO.setOrderAmount(orderAmount);
        findUncancelDTO.setDate(date);
        findUncancelDTO.setAllOrders(allOrders);
        return findUncancelDTO;
    }

    @RequestMapping("/getSumPrice")
    @ResponseBody
    public Integer getSumPrice() {
        LocalDate orderTime = LocalDate.now();
        if (orderDao.getSumPrice(orderTime) == null) {
            return 0;
        }else {
            return orderService.getSumPrice(orderTime);
        }

    }
}
