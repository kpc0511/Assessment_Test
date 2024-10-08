package com.maybank.platform.services.restapi.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisQuery {
    @AliasFor("key")
    String value() default "";

    @AliasFor("value")
    String key() default "";

    int[] parameters() default {};

    long activeTime() default 0;
}
