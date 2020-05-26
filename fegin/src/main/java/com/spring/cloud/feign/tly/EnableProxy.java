package com.spring.cloud.feign.tly;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({ProxyClientRegistrar.class})
public @interface EnableProxy {

    String[] value() default {};

    String[] basePackages() default {};

    Class<?>[] defaultConfiguration() default {};

}
