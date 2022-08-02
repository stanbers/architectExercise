package com.stanxu.controller;

import com.stanxu.pojo.UserAddress;
import com.stanxu.service.AddressService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
