package com.spring.cloud.feign.controller;

import com.spring.cloud.feign.service.HelloService;
import com.spring.cloud.feign.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
public class HelloController {

    @Autowired
    private HelloService helloService;

    @Autowired
    private TestService testService;

    @RequestMapping("/hi")
    public String home(@RequestParam String name) {
        int c = testService.add(1, 2);
        try {
            // 模拟 zuul请求超时
            Thread.sleep(5000);
        } catch (Exception e) {

        }
        return helloService.sayHiFromClientOne(name + c);
    }

}
