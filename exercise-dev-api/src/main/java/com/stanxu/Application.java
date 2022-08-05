package com.stanxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.stanxu.mapper")
@ComponentScan(basePackages = {"com.stanxu","org.n3r.idworker"})
@EnableScheduling
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

//    @GetMapping("/hello")
//    public String hello(@RequestParam(value = "name",defaultValue = "World") String name){
//        return String.format("Hello %s!",name);
//    }
}
