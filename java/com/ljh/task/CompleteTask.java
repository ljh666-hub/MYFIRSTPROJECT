package com.ljh.task;

import com.ljh.dao.CuponDao;
import com.ljh.dao.OrderDao;
import com.ljh.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class CompleteTask {
@Autowired
    private OrderDao orderDao;
@Autowired
   private CuponDao cuponDao;

    @Scheduled(cron = "0 0 * * * *")
    public void completeTask(){
        LocalDateTime time = LocalDateTime.now().plusHours(-7*24);
        orderDao.updateOrderStatus("已收货",time);
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void completeTask2(){
        LocalDate now =  LocalDate.now();
        cuponDao.updateByEndTime(now);
    }
}
