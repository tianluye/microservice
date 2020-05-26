package com.tly.logger.convert;

import ch.qos.logback.classic.pattern.ThrowableProxyConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import ch.qos.logback.classic.spi.ThrowableProxy;
import ch.qos.logback.core.CoreConstants;
import com.tly.logger.LogFormat;

/**
 * @author erhu
 *
 * Created by erhu on 2019/3/22.
 */
public class LogThrowableInformationConverter extends ThrowableProxyConverter {

    @Override
    public String convert(ILoggingEvent event) {
        if (event.getThrowableProxy() != null) {
            IThrowableProxy cause = event.getThrowableProxy();
            if (cause instanceof ThrowableProxy) {
                Throwable throwable = ((ThrowableProxy) cause).getThrowable();
                if (throwable instanceof LogFormat) {
                    return CoreConstants.EMPTY_STRING;
                }
            }
        }

        return super.convert(event);
    }

}
