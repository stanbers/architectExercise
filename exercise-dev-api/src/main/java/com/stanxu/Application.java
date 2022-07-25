package com.stanxu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@SpringBootApplication
@RestController
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name",defaultValue = "World") String name){
        return String.format("Hello %s!",name);
    }
}
//2.3.4.RELEASE/spring-boot-autoconfigure-2.3.4.RELEASE.jar!/org/springframework/boot/autoconfigure/transaction/TransactionAutoConfiguration.class
//2.3.4.RELEASE/spring-boot-autoconfigure-2.3.4.RELEASE-sources.jar!/org/springframework/boot/autoconfigure/transaction/TransactionAutoConfiguration.java
//https://www.youtube.com/redirect?event=video_description&redir_token=QUFFLUhqbXp2cFUzSjZWaHhxQTRZelpLb0lWaDhhVWJMUXxBQ3Jtc0tsYWNScE9QQ3pILXdSOURLSlkxVEFaOWg5cFhodU93SWxMN3l2c3hvbG1SQ3Z6Sm1sQWN6aXFGeEF2OHBXbGE4V0xpa1hYcWllM184dlVBdzdyVHpJVzE5bkw2V0kwMFpObGFWajRMc2EzQmZhN09RUQ&q=https%3A%2F%2Fwww.javainuse.com%2Fspring%2Fboot-transaction-propagation&v=zVRO-LELoSw
