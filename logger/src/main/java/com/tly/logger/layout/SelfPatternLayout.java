package com.tly.logger.layout;

import ch.qos.logback.classic.PatternLayout;
import com.tly.logger.convert.ThreadConvert;

public class SelfPatternLayout extends PatternLayout {

    static {
        defaultConverterMap.put("tc", ThreadConvert.class.getName());
    }

}
