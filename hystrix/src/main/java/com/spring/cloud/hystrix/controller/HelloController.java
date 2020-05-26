package com.spring.cloud.hystrix.controller;

import com.spring.cloud.hystrix.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        return helloService.sayHiFromClientOne(name);
    }

}
