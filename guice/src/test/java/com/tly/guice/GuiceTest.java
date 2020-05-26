package com.tly.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.tly.guice.module.HelloModule;
import com.tly.guice.module.ServerModule;
import com.tly.guice.service.HelloService;
import org.junit.Before;
import org.junit.Test;

public class GuiceTest {

//    @Inject
//    private HelloService helloService;

    @Inject
    private SessionManager sessionManager;


    @Before
    public void SetUp() {
        // 利用injectMembers，将当前所需的类具现化
        Injector injector = Guice.createInjector(
//                new HelloModule(),
                new ServerModule()
        );
        // 仅仅为当前类被标注了 Inject注解的属性赋值
        injector.injectMembers(this);
    }

    @Test
    public void testSendToPayment() {
//        helloService.sayHello("hi!");
        System.out.println(sessionManager);
    }

}
