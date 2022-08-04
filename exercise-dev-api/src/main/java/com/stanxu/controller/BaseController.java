package com.stanxu.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class BaseController {

    public final static Integer COMMON_PAGE_SIZE = 10;

    public final static Integer PAGE_SIZE = 20;

    public String payReturnUrl = "http://4mmfir.natappfree.cc/orders/notifyMerchantOrderPaid";

    public String  paymentUrl = "http://payment.t.mukewang.com/foodie-payment/payment/createMerchantOrder";
}
