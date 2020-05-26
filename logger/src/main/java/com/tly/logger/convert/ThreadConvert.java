package com.tly.logger.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

public class ThreadConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        String threadName = event.getThreadName();
        return threadName.substring(0, 8);
    }
}
