package com.example.myapplication.reflect_test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoWired {
    String value() default "";  //value的值表示getIntent(Key key)key的名称
}
