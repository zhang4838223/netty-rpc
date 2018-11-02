package com.zxj.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.zxj.netty.model.InvokerMethod;
import com.zxj.netty.model.NettyResponse;
import com.zxj.netty.model.NettyServerRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 封装了服务端对外暴露的方法/实例，
 * 用于ServerHandler接收请求后调用相应服务端接口进行处理
 */
public class InvokerContext {

    public static Map<String, InvokerMethod> methodMap;
    private static InvokerContext invokerContext = null;
    static {
        methodMap = new HashMap<>();
    }

    public InvokerContext() {
    }

    public static InvokerContext getInstance() {
        if (invokerContext == null) {
            invokerContext = new InvokerContext();
        }

        return invokerContext;
    }

    /**
     * 调用server服务处理客户端请求
     * @param nettyServerRequest 客户端请求
     * @return 处理结果
     */
    public NettyResponse execute(NettyServerRequest nettyServerRequest) {
        NettyResponse response = new NettyResponse();
        InvokerMethod invokerMethod = methodMap.get(nettyServerRequest.getCmd());
        if (invokerMethod == null) {
            return null;
        }

        Object bean = invokerMethod.getBean();
        Method method = invokerMethod.getMethod();
        Class paramType = method.getParameterTypes()[0];
        try {
            Object param = JSONObject.parseObject(JSONObject.toJSONString(nettyServerRequest.getContent()), paramType);
            Object result = method.invoke(bean,param);
            response.setId(nettyServerRequest.getId());
            response.setContent(result);
        } catch (Exception e){
            e.printStackTrace();
        }

        return response;
    }
}
