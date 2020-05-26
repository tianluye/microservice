package com.tly.logger.encoder;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.Layout;
import com.tly.logger.convert.ThreadConvert;
import com.tly.logger.layout.SelfPatternLayout;

public class ThreadEncoder extends PatternLayoutEncoder {

    @Override
    public void start() {
        SelfPatternLayout patternLayout = new SelfPatternLayout();
        patternLayout.setContext(context);
        patternLayout.setPattern(getPattern());
        patternLayout.setOutputPatternAsHeader(outputPatternAsHeader);
        patternLayout.start();
        this.layout = patternLayout;
        super.start();
    }
}
