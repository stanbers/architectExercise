package com.stanxu.controller;

import com.stanxu.pojo.UserAddress;
import com.stanxu.pojo.bo.UserAddressBO;
import com.stanxu.service.AddressService;
import com.stanxu.utils.JSONResult;
import com.stanxu.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "Address API", tags = "Address API")
@RequestMapping("address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @PostMapping("/list")
    @ApiOperation(value = "Query all address", notes = "Query all address", httpMethod = "POST")
    public JSONResult list(@RequestParam String userId){

        if (StringUtils.isBlank(userId)){
            JSONResult.errorMsg("user id is null !");
        }

        List<UserAddress> list = addressService.queryAllUserAddress(userId);
        return JSONResult.ok(list);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/add")
    @ApiOperation(value = "Add new address", notes = "Add new address", httpMethod = "POST")
    public JSONResult add(@RequestBody UserAddressBO userAddressBO){

        JSONResult validateAddress = checkAddress(userAddressBO);
        if (validateAddress.getStatus() != 200){
            return validateAddress;
        }

        addressService.addNewAddress(userAddressBO);

        return JSONResult.ok();
    }

    private JSONResult checkAddress(UserAddressBO addressBO) {
        String receiver = addressBO.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return JSONResult.errorMsg("收货人不能为空");
        }
        if (receiver.length() > 12) {
            return JSONResult.errorMsg("收货人姓名不能太长");
        }

        String mobile = addressBO.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return JSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return JSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return JSONResult.errorMsg("收货人手机号格式不正确");
        }

        String province = addressBO.getProvince();
        String city = addressBO.getCity();
        String district = addressBO.getDistrict();
        String detail = addressBO.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return JSONResult.errorMsg("收货地址信息不能为空");
        }

        return JSONResult.ok();
    }
}
