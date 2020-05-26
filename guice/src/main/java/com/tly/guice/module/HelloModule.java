package com.tly.guice.module;

import com.google.inject.AbstractModule;
import com.tly.guice.service.HelloService;
import com.tly.guice.service.PersonService;
import com.tly.guice.service.impl.HelloServiceImpl;
import com.tly.guice.service.impl.PersonServiceImpl;

public class HelloModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(HelloService.class).to(HelloServiceImpl.class);
        bind(PersonService.class).to(PersonServiceImpl.class);
    }
}
