package com.tly.logger.convert;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import com.tly.logger.LogFormat;

/**
 * @author erhu
 *
 * 业务ID转换,从MDC或LtLogFormat中获取bizID
 */
public class BizIDConvert extends ClassicConverter {
    @Override
    public String convert(ILoggingEvent event) {
        if (event.getThrowableProxy() != null) {
            IThrowableProxy cause = event.getThrowableProxy();
            if (cause instanceof ThrowableProxy) {
                Throwable throwable = ((ThrowableProxy) cause).getThrowable();
                if (throwable instanceof LogFormat) {
                    if(((LogFormat) throwable).getBizID()  != null){
                        return ((LogFormat) throwable).getBizID();
                    }
                }
            }
        }
        if(event.getMDCPropertyMap().containsKey(LogFormat.MDC_BIZ_ID)) {
            return event.getMDCPropertyMap().get(LogFormat.MDC_BIZ_ID);
        }
        return "";
    }
}
