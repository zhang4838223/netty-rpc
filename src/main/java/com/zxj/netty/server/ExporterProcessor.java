package com.zxj.netty.server;

import com.zxj.netty.annotation.RpcExpoter;
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
public class ExporterProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return o;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        if (o.getClass().isAnnotationPresent(RpcExpoter.class)) {
            Method[] methods = o.getClass().getDeclaredMethods();
            for (Method method : methods) {
                InvokerMethod im = new InvokerMethod(o, method);
                // 这里有多个接口,暂时默认第一个，后续优化处理
                String key = o.getClass().getInterfaces()[0].getName() + "." + method.getName();
                InvokerContext.methodMap.put(key, im);
//                Class<?>[] interfaces = o.getClass().getInterfaces();
//                for (Class<?> service : interfaces) {
//                    String key = service.getName() + "." + method.getName();
//                    InvokerContext.methodMap.put(key, im);
//                }
            }
        }
        return o;
    }
}
