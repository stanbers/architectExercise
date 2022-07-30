package com.stanxu.pojo.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "User object BO",description = "User entity that pass from F/E")
public class UserBO {
    @ApiModelProperty(value = "user name",name = "username",example = "Stan",required = true)
    private String username;

    @ApiModelProperty(value = "password",name = "password",example = "123456",required = true)
    private String password;

    @ApiModelProperty(value = "confirm password",name = "confirmPassword",example = "123456",required = false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
