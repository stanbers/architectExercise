package com.stanxu.controller;

import com.stanxu.enums.YesOrNo;
import com.stanxu.pojo.Carousel;
import com.stanxu.service.CarouselService;
import com.stanxu.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("index")
@Api(value = "Homepage", tags = "{API in homepage}")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @GetMapping("/carousel")
    @ApiOperation(value = "Get carousel list on homepage", notes = "Get carousel list on homepage", httpMethod = "GET")
    public JSONResult carousel(){

        List<Carousel> carouselList = carouselService.queryAll(YesOrNo.YES.type);
        return JSONResult.ok(carouselList);
    }
}
