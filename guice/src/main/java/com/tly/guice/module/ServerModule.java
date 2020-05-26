package com.tly.guice.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.tly.guice.Book;

public class ServerModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Integer.class).toProvider(() -> 18);
//        bind(Long.class).annotatedWith(Book.class).toInstance(12L);
    }

    @Provides
    Long generateSessionId() {
        // 名字可以随便起，当且仅有一个类型注入
        return 1234567L;
    }

    @Provides
    @Named("cookie")
    String genCookie() {
        return "asdqw";
    }

    @Provides
    @Named("address")
    String genAddress() {
        return "NanJing";
    }

    @Provides
    @Book
    Long genBookId() {
        return 12L;
    }
}
