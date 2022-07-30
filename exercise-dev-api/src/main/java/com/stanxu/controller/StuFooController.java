package com.stanxu.controller;

import com.stanxu.pojo.Stu;
import com.stanxu.service.StuService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StuFooController {

    final static private Logger logger = LoggerFactory.getLogger(StuFooController.class);
    @Autowired
    private StuService stuService;

    @GetMapping("/getStu")
    public Object getStuInfo(@RequestParam int id){
        logger.info("test log4j...");
        return stuService.getStuInfo(id);
    }

    @PostMapping("/saveStu")
    public Object saveStu(){
        stuService.saveStu();
        return "ok";
    }

    @PostMapping("/updateStu")
    public Object updateStu(int id){
        stuService.updateStu(id);
        return "ok";
    }

    @PostMapping("/deleteStu")
    public Object deleteStu(int id){
        stuService.deleteStu(id);
        return "ok";
    }
}

