package com.stanxu.service;

import com.stanxu.pojo.Users;
import com.stanxu.pojo.bo.UserBO;

public interface UserService {

    //Check if username exists
    boolean IsUsernameExist(String username);

    public Users createUser(UserBO userBO);
}
