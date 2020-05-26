package com.spring.cloud;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author tianluye
 * 注意这个 Proxy代理配置类（Feign自定义配置类）不能与主启动类所在包下
 * 防止主自动类包扫描到，将其注册到 Spring中。这个配置类是要在后面自己注册到 Spring中的
 */
@Configuration
public class SelfProxyConfiguration {

    @Bean
    public Object obj() {
        return new Object();
    }

}
