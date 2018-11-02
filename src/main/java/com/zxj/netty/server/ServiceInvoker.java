package com.zxj.netty.server;

import com.zxj.netty.model.InvokerMethod;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.lang.reflect.Method;

/**
 * spring容器初始化后，封装暴露出去的服务方法、对象实例到InvokerContext中
 */
@Component
public class ServiceInvoker implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o.getClass().isAnnotationPresent(Controller.class)) {
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method method : methods) {
                InvokerMethod im = new InvokerMethod(o, method);
                String key = o.getClass().getName() + "." + method.getName();
                InvokerContext.methodMap.put(key, im);
            }
        }
        return o;
    }
}
