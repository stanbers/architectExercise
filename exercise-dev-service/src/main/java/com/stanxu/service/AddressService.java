package com.stanxu.service;

import com.stanxu.pojo.UserAddress;
import com.stanxu.pojo.bo.UserAddressBO;

import java.util.List;

public interface AddressService {

    public List<UserAddress> queryAllUserAddress(String userId);

    public void addNewAddress(UserAddressBO userAddressBO);

    public void updateAddress(UserAddressBO userAddressBO);

    public void deleteAddress(String userId, String addressId);
}
