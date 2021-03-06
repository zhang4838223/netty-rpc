package com.zxj.netty.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 服务端对外暴露的接口注解
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
// 该注解标识的类可以被spring加载
@Component
public @interface RpcExpoter {

    String value() default "";
}
