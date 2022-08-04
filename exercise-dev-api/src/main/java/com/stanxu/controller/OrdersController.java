package com.stanxu.controller;

import com.stanxu.enums.YesOrNo;
import com.stanxu.pojo.bo.SubmitOrderBO;
import com.stanxu.service.OrderService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orders")
@Api(value = "Orders info API", tags = "Orders info API")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    @ApiOperation(value = "User place order", notes = "User place order", httpMethod = "POST")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO){

        // create order
        String orderId = orderService.createOrder(submitOrderBO);

        return JSONResult.ok(orderId);
    }
}
