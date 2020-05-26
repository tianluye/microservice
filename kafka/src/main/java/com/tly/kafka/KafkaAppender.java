package com.tly.kafka;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class KafkaAppender extends AppenderBase<ILoggingEvent> {

    private Logger logger = LoggerFactory.getLogger(KafkaAppender.class);

    /**
     * kafka生产者
     */
    private Producer<String, String> producer;

    @Override
    public void start() {
        super.start();
        if (producer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("topic", "topic_hello");
            props.put("retries", 0);
            props.put("batch.size", 0);
            // 延迟1s，1s内数据会缓存进行发送
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            producer = new KafkaProducer<>(props);
        }
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        String msg = eventObject.getFormattedMessage();
        logger.debug("向kafka推送日志开始:" + msg);
        ProducerRecord<String, String> record = new ProducerRecord<>("topic_hello", msg, msg);
        producer.send(record);
    }
}
