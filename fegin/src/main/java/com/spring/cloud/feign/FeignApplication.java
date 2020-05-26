package com.spring.cloud.feign;

import com.spring.cloud.SelfProxyConfiguration;
import com.spring.cloud.feign.tly.EnableProxy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableProxy(defaultConfiguration = {SelfProxyConfiguration.class})
public class FeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeignApplication.class, args);
	}

}
