package com.tly.logger.filter;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author yangkun
 */
@SuppressWarnings("unused")
public class ThresholdLessFilter extends Filter<ILoggingEvent> {
    private Level level;

    @Override
    public FilterReply decide(ILoggingEvent event) {
        if (!isStarted()) {
            return FilterReply.NEUTRAL;
        }
        if(event == null || event.getLevel() == null){
            return FilterReply.DENY;
        }

        if (!event.getLevel().isGreaterOrEqual(level)) {
            return FilterReply.ACCEPT;
        } else {
            return FilterReply.DENY;
        }
    }

    public void setLevel(String level) {
        this.level = Level.toLevel(level);
    }

    @Override
    public void start() {
        if (this.level != null) {
            super.start();
        }
    }
}