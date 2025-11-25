package com.ljh.service;

import com.ljh.pojo.Orders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface OrderService {
    boolean insertOrder(Orders orders);

    List<Orders> findByBrandId(int userId,String brandName,String orderStatus);

    boolean updateOrder(Orders orders);

    List<Orders> findAll();

    List<Orders> findByCondition(Orders orders);

    boolean deleteByOrderId(String orderId);

    List<Orders> orderPage(int nowPage);

    int countOrders();

    List<Orders> findByClient(LocalDateTime orderTime1, LocalDateTime orderTime2, String brandType,String orderStatus);

    List<Orders> findByOrderId(String orderId,String brandName);

    List<Orders> CountTotal(LocalDate orderTime1, LocalDate orderTime2);

    List<Orders> findCancel(LocalDate orderTime1, LocalDate orderTime2);

    Integer getSumPrice(LocalDate orderTime);

}
