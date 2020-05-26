package com.tly.logger;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Map;

@Aspect
@Component
/**
 * TlyLogCenter切面
 *
 * @author tianluye
 */
public class LoggerAop {

    private ThreadLocal<LinkedList<Map<String, String>>> ctxMapListThreadLocal = new ThreadLocal<>();

    @Pointcut("@annotation(tlyLogCenter)")
    public void logPoint(TlyLogCenter tlyLogCenter) {
    }

    @Around("logPoint(tlyLogCenter)")
    public Object around(ProceedingJoinPoint point, TlyLogCenter tlyLogCenter) throws Throwable {
        if (ctxMapListThreadLocal.get() == null) {
            ctxMapListThreadLocal.set(new LinkedList<>());
        }
        try {
            // 维护日志 MDC, 此时会覆盖之前的 MDC 里面设置的数据
            setupMDC(point, tlyLogCenter);
            // 保存当前方法的 MDC 信息, 用于后面恢复
            ctxMapListThreadLocal.get().add(MDC.getCopyOfContextMap());
            return point.proceed();
        } finally {
            LinkedList<Map<String, String>> ctxMapList = ctxMapListThreadLocal.get();
            // 移除当前方法的 MDC 信息
            ctxMapList.removeLast();

            if (ctxMapList.isEmpty()) {
                MDC.clear();
                ctxMapListThreadLocal.remove();
            } else {
                // 恢复上一个方法的 MDC 信息
                MDC.setContextMap(ctxMapList.getLast());
            }
        }
    }

    /**
     * 维护日志MDC
     */
    private void setupMDC(ProceedingJoinPoint point, TlyLogCenter tlyLogCenter) {
        TlyLogCenter classLogCenter = point.getTarget().getClass().getAnnotation(TlyLogCenter.class);
        if (classLogCenter != null) {
            // 获取类级别的 LogCenter
            setCommonMDC(point, classLogCenter);
        }
        // 方法级别的 LogCenter
        setCommonMDC(point, tlyLogCenter);
    }

    private void setCommonMDC(ProceedingJoinPoint point, TlyLogCenter logCenter) {
        // LogTag
        if (StringUtils.isNotEmpty(logCenter.logTag())) {
            MDC.put(LogFormat.MDC_LOG_TAG, logCenter.logTag());
        }
        if (StringUtils.isNotEmpty(logCenter.bizID())) {
            if (StringUtils.isEmpty(MDC.get(LogFormat.MDC_BIZ_ID))) {
                MDC.put(LogFormat.MDC_BIZ_ID, logCenter.bizID());
            }
        }
        //customLog1
        if (StringUtils.isNotEmpty(logCenter.customLog1())) {
            MDC.put(LogFormat.MDC_CUSTOM_LOG1, logCenter.customLog1());
        }
        //customLog2
        if (StringUtils.isNotEmpty(logCenter.customLog2())) {
            MDC.put(LogFormat.MDC_CUSTOM_LOG2, logCenter.customLog2());
        }
        //customLog3
        if (StringUtils.isNotEmpty(logCenter.customLog3())) {
            MDC.put(LogFormat.MDC_CUSTOM_LOG3, logCenter.customLog3());
        }
        //customLog4
        if (StringUtils.isNotEmpty(logCenter.customLog4())) {
            MDC.put(LogFormat.MDC_CUSTOM_LOG4, logCenter.customLog4());
        }
        //customLog5
        if (StringUtils.isNotEmpty(logCenter.customLog5())) {
            MDC.put(LogFormat.MDC_CUSTOM_LOG5, logCenter.customLog5());
        }
    }

}
