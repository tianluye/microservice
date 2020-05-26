package com.tly.guice.service.impl;

import com.google.inject.Inject;
import com.tly.guice.service.HelloService;
import com.tly.guice.service.PersonService;

public class HelloServiceImpl implements HelloService {

    private final PersonService personService;

    @Inject
    public HelloServiceImpl(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public void sayHello(String msg) {
        String fullName = personService.getFullName("tian", "luye");
        System.out.println(fullName + ", " + msg);
    }
}
