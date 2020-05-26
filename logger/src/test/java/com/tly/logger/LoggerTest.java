package com.tly.logger;

import com.tly.LoggerApplication;
import com.tly.logger.service.LoggerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.lang.invoke.MethodHandles;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = LoggerApplication.class)
@WebAppConfiguration
@ActiveProfiles("default")
public class LoggerTest {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private LoggerService loggerService;

    @Test
    public void testOne() {
        logger.info("测试方法[testOne]开始执行...");
        loggerService.infoLogOne();
        logger.info("测试方法[testOne]结束执行...");
    }

    @Test
    public void testTwo() {
        String result = loggerService.loggerFormat("mainPackage.sub.sample.Bar", 30);
        System.out.println("------" + result + "------");
    }

}
