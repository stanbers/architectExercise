package com.stanxu.service;

import com.stanxu.pojo.UserAddress;

import java.util.List;

public interface AddressService {

    public List<UserAddress> queryAllUserAddress(String userId);
}
