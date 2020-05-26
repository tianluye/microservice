package com.tly.logger.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.tly.logger.LogFormat;

/**
 * @author tianluye
 *
 * 日志TAG转换,从MDC或LtLogFormat中获取logTag
 * Created by erhu on 2019/3/21.
 */
public class LogTagConvert extends ClassicConverter {

    @Override
    public String convert(ILoggingEvent event) {
        if (event.getThrowableProxy() != null) {
            IThrowableProxy cause = event.getThrowableProxy();
            if (cause instanceof ThrowableProxy) {
                Throwable throwable = ((ThrowableProxy) cause).getThrowable();
                if (throwable instanceof LogFormat) {
                    if(((LogFormat) throwable).getLogTag() != null){
                        return ((LogFormat) throwable).getLogTag();
                    }
                }
            }
        }
        if(event.getMDCPropertyMap().containsKey(LogFormat.MDC_LOG_TAG)) {
            return event.getMDCPropertyMap().get(LogFormat.MDC_LOG_TAG);
        }
        return "";
    }
}
