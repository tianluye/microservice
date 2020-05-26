package com.tly.guice;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// 运行时起作用
@Retention(RetentionPolicy.RUNTIME)
//让其绑定注解
@BindingAnnotation
public @interface Book {
}
