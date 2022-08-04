package com.stanxu.controller;

import com.stanxu.enums.OrderStatusEnum;
import com.stanxu.enums.PayMethod;
import com.stanxu.pojo.OrderStatus;
import com.stanxu.pojo.bo.SubmitOrderBO;
import com.stanxu.pojo.vo.MerchantOrdersVO;
import com.stanxu.pojo.vo.OrderVO;
import com.stanxu.service.OrderService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("orders")
@Api(value = "Orders info API", tags = "Orders info API")
public class OrdersController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(OrdersController.class);

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/create")
    @ApiOperation(value = "User place order", notes = "User place order", httpMethod = "POST")
    public JSONResult create(@RequestBody SubmitOrderBO submitOrderBO){

        if(submitOrderBO.getPayMethod() != PayMethod.WEIXIN.type &&
            submitOrderBO.getPayMethod() != PayMethod.ALIPAY.type) {
            return JSONResult.errorMsg("pay method does not support !");
        }
        // create order
        OrderVO orderVO = orderService.createOrder(submitOrderBO);
        String orderId = orderVO.getOrderId();

        // send current order to pay system
        MerchantOrdersVO  merchantOrdersVO = orderVO.getMerchantOrdersVO();
        // set to 1 cent for testing.
        merchantOrdersVO.setAmount(1);
        merchantOrdersVO.setReturnUrl(payReturnUrl);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.add("imoocUserId","1543468846");
        httpHeaders.add("password","y45t-54tg");

        HttpEntity<MerchantOrdersVO> httpEntity = new HttpEntity<>(merchantOrdersVO,httpHeaders);
        ResponseEntity<JSONResult> response = restTemplate.postForEntity(paymentUrl,httpEntity,JSONResult.class);
        JSONResult paymentResult = response.getBody();
        if (paymentResult.getStatus() != 200){
            JSONResult.errorMsg("build merchant order failed ! pls contact administrator !");
        }

        logger.info("&&&&&&&&&& " + paymentResult.getMsg());
        logger.info("**********order id is : " + orderId);
        return JSONResult.ok(orderId);
    }

    @PostMapping("/notifyMerchantOrderPaid")
    public Integer notifyMerchantOrderPaid(String merchantOrderId){

        // after pay done, return pre-pay transaction link from pay system to merchant b/e system
        // sample: http://localhost:8088/orders/notifyMerchantOrderPaid
        // meanwhile, update the pay status to WAIT_DELIVER(20, "已付款，待发货")
        orderService.notifyMerchantOrderPaid(merchantOrderId, OrderStatusEnum.WAIT_DELIVER.type);

        return HttpStatus.OK.value();
    }

    //http://qwqc4g.natappfree.cc
    //http://qbssid.natappfree.cc

    @PostMapping("/getPaidOrderInfo")
    public JSONResult getPaidOrderInfo(String orderId){

        OrderStatus orderStatus = orderService.getPaidOrderInfo(orderId);

        return JSONResult.ok(orderStatus);
    }

}
