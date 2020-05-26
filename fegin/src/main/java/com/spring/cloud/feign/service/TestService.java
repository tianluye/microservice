package com.spring.cloud.feign.service;

import com.spring.cloud.feign.tly.ProxyClient;

@ProxyClient(name = "testService", primary = true)
public interface TestService {

    int add(int a, int b);

}
