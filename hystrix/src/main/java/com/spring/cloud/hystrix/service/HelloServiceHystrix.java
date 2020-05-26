package com.spring.cloud.hystrix.service;

import org.springframework.stereotype.Component;

@Component
public class HelloServiceHystrix implements HelloService {

    @Override
    public String sayHiFromClientOne(String name) {
        return "Sorry, " + name + ", please try again after moment.";
    }
}
