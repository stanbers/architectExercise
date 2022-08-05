package com.stanxu.config;

import com.stanxu.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderJobs {

    @Autowired
    private OrderService orderService;

    @Scheduled(cron = "0/3 * * * * ?")
    public void autoCloseOrderJob(){
        orderService.closeOrder();
    }
}
