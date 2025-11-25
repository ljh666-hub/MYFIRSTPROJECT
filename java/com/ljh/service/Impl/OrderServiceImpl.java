package com.ljh.service.Impl;

import com.ljh.dao.OrderDao;
import com.ljh.pojo.Orders;
import com.ljh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Override
    public boolean insertOrder(Orders orders) {
        return orderDao.insertOrder(orders)>0;
    }

    @Override
    public List<Orders> findByBrandId(int userId,String brandName,String orderStatus) {
        return orderDao.findByBrandId(userId,brandName,orderStatus);
    }

    @Override
    public boolean updateOrder(Orders orders) {
        return orderDao.updateOrder(orders)>0;
    }

    @Override
    public List<Orders> findAll() {
        return orderDao.findAll();
    }

    @Override
    public List<Orders> findByCondition(Orders orders) {
        return orderDao.findByCondition(orders);
    }

    @Override
    public boolean deleteByOrderId(String orderId) {
        return orderDao.deleteByOrderId(orderId)>0;
    }

    @Override
    public List<Orders> orderPage(int nowPage) {
        return orderDao.orderPage(nowPage);
    }

    @Override
    public int countOrders() {
        return orderDao.countOrders();
    }

    @Override
    public List<Orders> findByClient(LocalDateTime orderTime1, LocalDateTime orderTime2, String brandType,String orderStatus) {
        return orderDao.findByClient(orderTime1,orderTime2,brandType,orderStatus);
    }

    @Override
    public List<Orders> findByOrderId(String orderId,String brandName) {
        return orderDao.findByOrderId(orderId,brandName);
    }

    @Override
    public List<Orders> CountTotal(LocalDate orderTime1, LocalDate orderTime2) {
        return orderDao.CountTotal(orderTime1,orderTime2);
    }

    @Override
    public List<Orders> findCancel(LocalDate orderTime1, LocalDate orderTime2) {
        return orderDao.findCancel(orderTime1,orderTime2);
    }

    @Override
    public Integer getSumPrice(LocalDate orderTime) {
        return orderDao.getSumPrice(orderTime);
    }
}
