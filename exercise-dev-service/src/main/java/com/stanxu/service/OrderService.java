package com.stanxu.service;

import com.stanxu.pojo.OrderStatus;
import com.stanxu.pojo.bo.SubmitOrderBO;
import com.stanxu.pojo.vo.OrderVO;

public interface OrderService {

    public OrderVO createOrder(SubmitOrderBO submitOrderBO);

    public void notifyMerchantOrderPaid(String merchantOrderId, Integer orderStatus);

    public OrderStatus getPaidOrderInfo(String orderId);

    public void closeOrder();

}
