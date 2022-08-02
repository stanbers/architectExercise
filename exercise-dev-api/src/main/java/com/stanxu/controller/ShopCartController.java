package com.stanxu.controller;

import com.stanxu.pojo.bo.ShopcartItemBO;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@Api(value = "Shopping cart API", tags = "Shopping cart API")
@RequestMapping("shopcart")
public class ShopCartController {

    @ApiOperation(value = "Add item to shopping cart",notes = "Add item to shopping cart", httpMethod = "POST")
    @PostMapping("/add")
    public JSONResult add(
            @RequestParam String userId,
            @RequestBody ShopcartItemBO shopcartItemBO,
            HttpServletRequest request,
            HttpServletResponse response){

        if (StringUtils.isBlank(userId)){
            return JSONResult.errorMsg("");
        }

        // TODO will use redis after user login

//        System.out.println(shopcartItemBO);

        return JSONResult.ok();
    }

    @ApiOperation(value = "Delete item from shopping cart",notes = "Delete item from shopping cart", httpMethod = "POST")
    @PostMapping("/del")
    public JSONResult del(
            @RequestParam String userId,
            @RequestParam String itemSpecId,
            HttpServletRequest request,
            HttpServletResponse response){

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(itemSpecId)){
            return JSONResult.errorMsg("");
        }

        // TODO will use redis after user login, need to delete data from redis as well.

//        System.out.println(shopcartItemBO);

        return JSONResult.ok();
    }
}
