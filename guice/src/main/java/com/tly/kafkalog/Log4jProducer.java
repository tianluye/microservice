package com.tly.kafkalog;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class Log4jProducer {
    private static final Logger LOG = Logger.getLogger(Log4jProducer.class);

    public static void main(String[] args) {
        LOG.setLevel(Level.INFO);
        LOG.debug("这是一条debug级别的日志！");
        LOG.info("这是一条info级别的日志！");
        LOG.error("这是一条error级别的日志！");
        LOG.fatal("这是一条fatal级别的日志！");
    }
}
