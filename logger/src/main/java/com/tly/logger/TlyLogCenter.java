package com.tly.logger;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
/**
 * 开启日志上下文管理注解
 * 可以标注在方法和类上
 */
public @interface TlyLogCenter {

    String logTag() default "";

    String bizID() default "";

    boolean accessLog() default false;

    long logThreshold() default 200L;

    String customLog1() default "";

    String customLog2() default "";

    String customLog3() default "";

    String customLog4() default "";

    String customLog5() default "";

}
