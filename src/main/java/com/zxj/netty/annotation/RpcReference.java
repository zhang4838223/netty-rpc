package com.zxj.netty.annotation;

import java.lang.annotation.*;

/**
 * 消费端引入的远程调用注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RpcReference {
}
