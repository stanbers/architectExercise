package com.stanxu.service.impl;

import com.stanxu.enums.OrderStatusEnum;
import com.stanxu.enums.PayMethod;
import com.stanxu.enums.YesOrNo;
import com.stanxu.mapper.OrderItemsMapper;
import com.stanxu.mapper.OrderStatusMapper;
import com.stanxu.mapper.OrdersMapper;
import com.stanxu.pojo.*;
import com.stanxu.pojo.bo.SubmitOrderBO;
import com.stanxu.pojo.vo.MerchantOrdersVO;
import com.stanxu.pojo.vo.OrderVO;
import com.stanxu.service.AddressService;
import com.stanxu.service.ItemService;
import com.stanxu.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private Sid sid;

    @Autowired
    private AddressService addressService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private OrderItemsMapper orderItemsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    @Autowired
    private OrderStatusMapper orderStatusMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public OrderVO createOrder(SubmitOrderBO submitOrderBO) {
        String userId = submitOrderBO.getUserId();
        String itemSpecIds = submitOrderBO.getItemSpecIds();
        String addressId = submitOrderBO.getAddressId();
        Integer payMethod = submitOrderBO.getPayMethod();
        String leftMsg = submitOrderBO.getLeftMsg();

        //setup post fee to 0
        Integer postAmount = 0;

        String orderId = sid.nextShort();

        UserAddress userAddress = addressService.queryUserAddress(userId,addressId);

        Orders newOrder = new Orders();
        newOrder.setUserId(userId);
        newOrder.setId(orderId);
        newOrder.setLeftMsg(leftMsg);
        newOrder.setPayMethod(payMethod);
        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setReceiverName(userAddress.getReceiver());
        newOrder.setReceiverAddress(userAddress.getProvince() + " " + userAddress.getCity() + " " + userAddress.getDistrict() + "" + userAddress.getDetail());
        newOrder.setReceiverMobile(userAddress.getMobile());
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());

        //iterate specify item via itemSpecIds

        String specIds [] = itemSpecIds.split(",");
        Integer totalPayAmount = 0;
        Integer totalPayDiscountAmount = 0;

        for (String specId : specIds){

            //TODO buy counts should be queried from Redis shopping cart
            int buyCounts = 1;

            // get item price of each item via specId
            ItemsSpec itemsSpec = itemService.queryItemSpecBySpecId(specId);
            totalPayAmount += itemsSpec.getPriceNormal();
            totalPayDiscountAmount += itemsSpec.getPriceDiscount();

            // get item info and item img
            String itemId = itemsSpec.getItemId();
            Items item = itemService.queryItemById(itemId);
            String itemImgUrl = itemService.queryItemsMainImgByItemId(itemId);

            // insert to orderItems table
            String subOrderId = sid.nextShort();
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            subOrderItem.setItemImg(itemImgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(specId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);

            //need to decrease related item inventory from Item spec table after order placed.
            itemService.decreaseInventoryAfterOrderPlaced(buyCounts,specId);
        }

        newOrder.setPostAmount(postAmount);
        newOrder.setTotalAmount(totalPayAmount);
        newOrder.setRealPayAmount(totalPayDiscountAmount);
        ordersMapper.insert(newOrder);

        // insert to order status table
        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(orderId);
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        orderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(orderStatus);

        // build merchant order
        MerchantOrdersVO merchantOrdersVO = new MerchantOrdersVO();
        merchantOrdersVO.setMerchantOrderId(orderId);
        merchantOrdersVO.setMerchantUserId(userId);
        merchantOrdersVO.setAmount(totalPayDiscountAmount+postAmount);
        merchantOrdersVO.setPayMethod(payMethod);

        // build customize VO for pay system
        OrderVO orderVO = new OrderVO();
        orderVO.setMerchantOrdersVO(merchantOrdersVO);
        orderVO.setOrderId(orderId);

        return orderVO;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void notifyMerchantOrderPaid(String merchantOrderId) {

        OrderStatus orderStatus = new OrderStatus();
        orderStatus.setOrderId(merchantOrderId);
        orderStatus.setPayTime(new Date());
        orderStatus.setOrderStatus(OrderStatusEnum.WAIT_DELIVER.type);
        orderStatusMapper.updateByPrimaryKeySelective(orderStatus);
    }
}
