package com.zxj.netty.model;

import java.lang.reflect.Method;

public class InvokerMethod {

    private Object bean;
    private Method method;

    public InvokerMethod() {
    }

    public InvokerMethod(Object bean, Method method) {
        this.bean = bean;
        this.method = method;
    }

    public Object getBean() {
        return bean;
    }

    public void setBean(Object bean) {
        this.bean = bean;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
