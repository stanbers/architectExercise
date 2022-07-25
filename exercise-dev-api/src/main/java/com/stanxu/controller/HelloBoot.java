package com.stanxu.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
public class HelloBoot {

    //@GetMapping("/hello")
    public String hello(){
        return "hell boot~";
    }
}
