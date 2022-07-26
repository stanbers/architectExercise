package com.stanxu.service.impl;

import com.stanxu.enums.YesOrNo;
import com.stanxu.mapper.UserAddressMapper;
import com.stanxu.pojo.UserAddress;
import com.stanxu.pojo.bo.UserAddressBO;
import com.stanxu.service.AddressService;
import org.apache.catalina.User;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private Sid sid;

    @Autowired
    private UserAddressMapper userAddressMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAllUserAddress(String userId) {

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);

        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);

        return userAddressList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void addNewAddress(UserAddressBO userAddressBO) {

        // if user has not any address, then set it to default address.
        Integer is_default = 0;

        List<UserAddress> userAddressList = this.queryAllUserAddress(userAddressBO.getUserId());
        if (userAddressList == null || userAddressList.isEmpty() || userAddressList.size() ==0){
            is_default = 1;
        }

        // if user has address, then just add.
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBO,userAddress);

        userAddress.setId(sid.nextShort());
        userAddress.setIsDefault(is_default);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateAddress(UserAddressBO userAddressBO) {

        UserAddress updateAddress = new UserAddress();
        BeanUtils.copyProperties(userAddressBO, updateAddress);

        updateAddress.setId(userAddressBO.getAddressId());
        updateAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteAddress(String userId, String addressId) {
        UserAddress userAddress = new UserAddress();

        userAddress.setId(addressId);
        userAddress.setUserId(userId);

        userAddressMapper.delete(userAddress);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateAddressToDefault(String userid, String addressId) {
        //1.update default address's is_default to 0

        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userid);
        userAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> userAddressList = userAddressMapper.select(userAddress);
        for (UserAddress address : userAddressList){
            address.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(address);
        }


        //2.update target address's is_default to 1
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userid);
        defaultAddress.setIsDefault(YesOrNo.YES.type);

        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);


    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddress(String userId, String addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setUserId(userId);
        singleAddress.setId(addressId);

        return userAddressMapper.selectOne(singleAddress);
    }
}
