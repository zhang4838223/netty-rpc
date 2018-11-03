package com.zxj.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.zxj.netty.annotation.RpcReference;
import com.zxj.netty.model.NettyRequest;
import com.zxj.netty.model.NettyResponse;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 消费端引用远程服务时，获取远程服务的代理类进行调用
 */
@Component
public class RemoteInvokerProxy implements BeanPostProcessor {
    /**
     * 对象属性的方法及属性类型的对应关系
     */
    private final Map<Method, Class> map = new HashMap<>();
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 对引用的远程服务设置代理
            if (field.isAnnotationPresent(RpcReference.class) && field.getType().isInterface()) {
                field.setAccessible(true);

                // 封装Method-Class映射关系
                final Map<Method, Class> map = buildMethodClassMap(field);
                Enhancer enhancer = new Enhancer();
                enhancer.setInterfaces(new Class[]{field.getType()});
                enhancer.setCallback(new MethodInterceptor() {
                    @Override
                    public Object intercept(Object bean, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                        // 发送netty请求
                        NettyRequest request = new NettyRequest();
                        request.setContent(args[0]);
                        // 全类名.方法名
                        request.setCmd(map.get(method).getName() + "." + method.getName());

                        NettyResponse response = RpcClient.send(request);
                        Class<?> returnType = method.getReturnType();
                        Object result = JSONObject.parseObject(JSONObject.toJSONString(response.getContent()), returnType);
                        return result;
                    }
                });

                try {
                    // 设置该实例被RpcReference注解的成员变量值为相应代理
                    field.set(o, enhancer.create());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

    private Map<Method, Class> buildMethodClassMap(Field field) {
        Method[] declaredMethods = field.getType().getDeclaredMethods();
        for (Method m : declaredMethods) {
            if (!map.containsKey(m)) {
                map.put(m, field.getType());
            }
        }
        return map;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
