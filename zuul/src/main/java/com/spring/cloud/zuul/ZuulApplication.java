package com.spring.cloud.zuul;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableZuulProxy
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class ZuulApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZuulApplication.class, args);
	}

	/**
	 * 若不在配置里面配置熔断超时时间，那么很可能会抛出 at org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter.findZuulException 异常
	 * 因为默认的 zuul超时时间很短，直接就走到熔断里了。
	 *
	 * ribbon:
	 	 ConnectTimeout: 10000
	     ReadTimeout: 10000
	 * 具体可参考博客 https://blog.csdn.net/tianyaleixiaowu/article/details/78772269
	 */

}
