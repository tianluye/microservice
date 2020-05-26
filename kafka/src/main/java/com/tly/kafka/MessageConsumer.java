package com.tly.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 监听topic_hello主题产生的消息
 */
@Component
public class MessageConsumer {

    @KafkaListener(topics = "topic_hello")
    public void listen (ConsumerRecord<?, ?> record){
        System.out.println("接收到的消息为："+record.value());
    }
}
