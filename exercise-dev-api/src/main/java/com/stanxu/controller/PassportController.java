package com.stanxu.controller;

import com.stanxu.pojo.Users;
import com.stanxu.pojo.bo.UserBO;
import com.stanxu.service.UserService;
import com.stanxu.utils.CookieUtils;
import com.stanxu.utils.JSONResult;
import com.stanxu.utils.JsonUtils;
import com.stanxu.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Api(value = "User register and sign in", tags = "For user register and sign in")
@RestController
@RequestMapping("passport")
public class PassportController {

    @Autowired
    private UserService userService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @GetMapping("/isUsernameExist")
    @ApiOperation(value = "Check user exists", notes = "Check user exists", httpMethod = "GET")
    public JSONResult isUsernameExist(@RequestParam(value = "username") String username){

        //username cannot be empty
        if(StringUtils.isBlank(username)){
            return JSONResult.errorMsg("username cannot be empty !");
        }

        //username exists
        boolean isUsernameExist = userService.IsUsernameExist(username);
        if (isUsernameExist){
            return JSONResult.errorMsg("username exists !");
        }

        //username does not exist
        return JSONResult.ok();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/register")
    @ApiOperation(value = "Register User", notes = "Register User", httpMethod = "POST")
    public JSONResult register(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response){


        String username = userBO.getUsername();
        String password = userBO.getPassword();
        String confirmPassword = userBO.getConfirmPassword();

        //0. check username and pwd are not empty
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmPassword)){
            return JSONResult.errorMsg("username or password cannot be empty !");
        }

        //1. check username exists
        boolean isUsernameExists = userService.IsUsernameExist(username);
        if (isUsernameExists){
            return JSONResult.errorMsg("username exists !");
        }

        //2. check the pwd length must not be less than 6 digits
        if (password.length() < 6){
            return JSONResult.errorMsg("the pwd length must not be less than 6 digits !");
        }

        //3. check if the two pwds are the same
        if (!password.equals(confirmPassword)){
            return JSONResult.errorMsg("the two pwds are not the same !");
        }

        //4. register user
        Users user = userService.createUser(userBO);

        user = setPropertyNull(user);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);

        //created user done.
        return JSONResult.ok(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @PostMapping("/login")
    @ApiOperation(value = "User login", notes = "User login", httpMethod = "POST")
    public JSONResult login(@RequestBody UserBO userBO, HttpServletRequest request, HttpServletResponse response)throws Exception{


        String username = userBO.getUsername();
        String password = userBO.getPassword();

        //0. check username and pwd are not empty
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ){
            return JSONResult.errorMsg("username or password cannot be empty !");
        }

        //1. user login
        Users user = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));

        if (user == null){
            return JSONResult.errorMsg("username or password incorrect !");
        }

        user = setPropertyNull(user);

        CookieUtils.setCookie(request,response,"user", JsonUtils.objectToJson(user),true);

        //login user done.
        return JSONResult.ok(user);
    }

    //set private property to null.
    private Users setPropertyNull(Users users){
        users.setPassword(null);
        users.setRealname(null);
        users.setUpdatedTime(null);
        users.setCreatedTime(null);
        users.setBirthday(null);
        users.setMobile(null);

        return users;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @PostMapping("/logout")
    @ApiOperation(value = "User logout", notes = "User logout", httpMethod = "POST")
    public JSONResult logout(@RequestParam String userId, HttpServletRequest request, HttpServletResponse response){

        CookieUtils.deleteCookie(request,response,"user");

        //TODO need to clear out shopping cart when user logout
        //TODO need to clear out user data in distribution session when user logout

        return JSONResult.ok();
    }
}
