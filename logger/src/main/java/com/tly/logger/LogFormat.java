package com.tly.logger;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author tianluye
 *
 * <pre>
 * 注意: 在 log.info() 或者在 log.error() 中的使用 LtLogFormat 的时候一定要记得最后调用 build() 方法, 并且要放在参数中的最后一个.
 * 比如: log.info("query order, orderNo={}, userId={}", "NJ10000", "1000123", LtLogFormat.builder().logTag("order.query").build());
 * 不能写成: log.info("query order, orderNo={}, userId={}", "NJ10000", LtLogFormat.builder().logTag("order.query").build(), "1000123");
 * </pre>
 */
@Builder()
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LogFormat extends RuntimeException {
    public final static String MDC_LOG_TAG     = "MDC_LOG_TAG";
    public final static String MDC_BIZ_ID      = "MDC_BIZ_ID";
    public final static String MDC_CLIENT_IP   = "MDC_CLIENT_IP";
    public final static String MDC_URL         = "MDC_URL";
    public final static String MDC_SITEUID     = "MDC_SITEUID";
    public final static String MDC_LANGUAGE    = "MDC_LANGUAGE";
    public final static String MDC_CUSTOM_LOG1 = "MDC_CUSTOM_LOG1";
    public final static String MDC_CUSTOM_LOG2 = "MDC_CUSTOM_LOG2";
    public final static String MDC_CUSTOM_LOG3 = "MDC_CUSTOM_LOG3";
    public final static String MDC_CUSTOM_LOG4 = "MDC_CUSTOM_LOG4";
    public final static String MDC_CUSTOM_LOG5 = "MDC_CUSTOM_LOG5";
    public final static String MDC_ERR_CODE    = "MDC_ERR_CODE";
    public final static String MDC_COST_TIME   = "MDC_COST_TIME";
    public final static String MDC_KV_STRING   = "MDC_KV_STRING";
    public final static String MDC_KV_JSON     = "MDC_KV_JSON";
    public final static String MDC_KV_JSON_CHECK = "MDC_KV_JSON_CHECK";
    public static final String MDC_HTTP_METHOD   = "MDC_HTTP_METHOD";
    public final static String MDC_TRACEID       = "X-B3-TraceId";
    public final static String MDC_SPANID        = "X-B3-SpanId";
    public final static String MDC_PARENTID      = "X-B3-ParentSpanId";

    public static final String STR_FALSE = "false";

    public enum LogType {
        /**
         * 访问日志
         */
        ACCESS("ACCESS"),
        /**
         * 事件日志
         */
        EVENT("EVENT"),
        /**
         * RPC
         */
        RPC("RPC"),
        /**
         * 调用日志
         */
        OTHER("OTHER")
        ;
        private String type;

        LogType(String type) {
            this.type = type;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * 日志标签
     */
    private String logTag;
    /**
     * 业务主键ID
     */
    private String bizID;
    /**
     * 日志类型
     */
    @Builder.Default
    private LogType type = LogType.ACCESS;
    /**
     * 日志标题
     */
    private String title;
    /**
     * 耗时 毫秒
     */
    private long costTime;
    /**
     * 错误码
     */
    private String errCode;
    /**
     * HTTP method
     */
    private String httpMethod;
    /**
     * SiteUid
     */
    private String siteUid;
    /**
     * url
     */
    private String url;
    /**
     * 语种
     */
    private String language;
    /**
     * clientIP
     */
    private String clientIP;

    private String traceID;

    private String spanID;

    private String parentID;

    /**
     * 自定义字段
     */
    private String customLog1;
    private String customLog2;
    private String customLog3;
    private String customLog4;
    private String customLog5;

    private String logDebugTag;

    /**
     * logTag 为频繁设置的字段, 添加快捷方法.
     */
    public static LogFormat logTag(String logTag) {
        return LogFormat.create()
                .withLogTag(logTag);
    }

    public static LogFormat logDebugTag(String logDebugTag) {
        return LogFormat.create()
                .withLogDebugTag(logDebugTag);
    }

    public LogFormat withLogTag(String logTag) {
        this.logTag = logTag;
        return this;
    }

    public LogFormat withBizID(String bizID) {
        this.bizID = bizID;
        return this;
    }

    public LogFormat withType(LogType type) {
        this.type = type;
        return this;
    }

    public LogFormat withTitle(String title) {
        this.title = title;
        return this;
    }

    public LogFormat withCostTime(long costTime) {
        this.costTime = costTime;
        return this;
    }

    public LogFormat withErrCode(String errCode) {
        this.errCode = errCode;
        return this;
    }

    public LogFormat withCustomLog1(String customLog1) {
        this.customLog1 = customLog1;
        return this;
    }

    public LogFormat withCustomLog2(String customLog2) {
        this.customLog2 = customLog2;
        return this;
    }

    public LogFormat withCustomLog3(String customLog3) {
        this.customLog3 = customLog3;
        return this;
    }

    public LogFormat withCustomLog4(String customLog4) {
        this.customLog4 = customLog4;
        return this;
    }

    public LogFormat withCustomLog5(String customLog5) {
        this.customLog5 = customLog5;
        return this;
    }

    public LogFormat withLogDebugTag(String logDebugTag) {
        this.logDebugTag = logDebugTag;
        return this;
    }

    public LogFormat withHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public LogFormat withSiteUid(String siteUid) {
        this.siteUid = siteUid;
        return this;
    }

    public LogFormat withUrl(String url) {
        this.url = url;
        return this;
    }

    public LogFormat withLanguage(String language) {
        this.language = language;
        return this;
    }

    public LogFormat withClientIP(String clientIP) {
        this.clientIP = clientIP;
        return this;
    }

    public LogFormat withTraceID(String traceID) {
        this.traceID = traceID;
        return this;
    }

    public LogFormat withSpanID(String spanID) {
        this.spanID = spanID;
        return this;
    }

    public LogFormat withParentID(String parentID) {
        this.parentID = parentID;
        return this;
    }

    public static LogFormat create() {
        return new LogFormat();
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }

}
