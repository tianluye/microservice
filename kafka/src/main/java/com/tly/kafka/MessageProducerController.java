package com.tly.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 模拟请求产生消息
 */
@RestController
public class MessageProducerController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private KafkaTemplate<String,Object> kafkaTemplate;

    @RequestMapping("/hello/{name}")
    public String sayHello(@PathVariable(value = "name") String name){
        logger.info("test kafka...");
        // 使用kafka模板发送信息
        // topic_hello为消息订阅主题
        kafkaTemplate.send("topic_hello", "你好！"+name);
        return name;
    }

}
