package com.stanxu.service.impl;

import com.stanxu.mapper.UserAddressMapper;
import com.stanxu.pojo.UserAddress;
import com.stanxu.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Override
    public List<UserAddress> queryAllUserAddress(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);

        return userAddressList;
    }
}
