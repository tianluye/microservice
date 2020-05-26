package com.tly.guice.service.impl;

import com.tly.guice.service.PersonService;

public class PersonServiceImpl implements PersonService {

    @Override
    public String getFullName(String pre, String last) {
        return pre + "-" + last;
    }

}
