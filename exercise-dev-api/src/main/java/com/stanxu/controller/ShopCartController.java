package com.stanxu.controller;

import com.stanxu.pojo.Items;
import com.stanxu.pojo.ItemsImg;
import com.stanxu.pojo.ItemsParam;
import com.stanxu.pojo.ItemsSpec;
import com.stanxu.pojo.bo.ShopcartItemBO;
import com.stanxu.pojo.bo.UserBO;
import com.stanxu.pojo.vo.ItemInfoVO;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
}
