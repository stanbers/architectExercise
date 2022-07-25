package com.stanxu.controller;

import com.stanxu.service.UserService;
import com.stanxu.utils.JSONResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @GetMapping("/isUsernameExist")
    public JSONResult isUsernameExist(@RequestParam(value = "username") String username){

        //username cannot be empty
        if(StringUtils.isBlank(username)){
            return JSONResult.errorMsg("username cannot be empty !");
        }

        //username exists
        boolean isUsernameExist = userService.IsUsernameExist(username);
        if (isUsernameExist){
            return JSONResult.errorMap("username exists !");
        }

        //username does not exist
        return JSONResult.ok();
    }
}
