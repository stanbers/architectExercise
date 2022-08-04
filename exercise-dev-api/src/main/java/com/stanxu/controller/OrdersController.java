package com.stanxu.controller;

import com.stanxu.enums.PayMethod;
import com.stanxu.enums.YesOrNo;
import com.stanxu.pojo.bo.SubmitOrderBO;
import com.stanxu.pojo.vo.OrderVO;
import com.stanxu.service.OrderService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

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

        if(submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
            submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return JSONResult.errorMsg("pay method does not support !");
        }
        // create order
        OrderVO orderVO = orderService.createOrder(submitOrderBO);

        return JSONResult.ok(orderVO.getOrderId());
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){

        // after pay done, return pre-pay transaction link from pay system to merchant b/e system
        // sample: http://localhost:8088/orders/notifyMerchantOrderPaid
        // meanwhile, update the pay status to WAIT_DELIVER(20, "已付款，待发货")
        orderService.notifyMerchantOrderPaid(merchantOrderId);

        return HttpStatus.OK.value();
    }
}
