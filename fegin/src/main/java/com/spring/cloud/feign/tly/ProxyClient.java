package com.spring.cloud.feign.tly;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.core.annotation.AliasFor;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ProxyClient {

    @AliasFor("name")
    String value() default "";

    @AliasFor("value")
    String name() default "";

    boolean primary() default true;

}
