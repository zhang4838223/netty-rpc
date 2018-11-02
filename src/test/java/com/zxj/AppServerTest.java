package com.zxj;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zxj.netty.client.NettyClient;
import com.zxj.netty.client.RpcClient;
import com.zxj.netty.model.NettyRequest;
import com.zxj.netty.model.NettyResponse;
import com.zxj.netty.sale.model.Order;
import org.junit.Test;

/**
 * Unit test for simple AppServer.
 */
public class AppServerTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        NettyRequest request = new NettyRequest();
        request.setContent("access server!");
        NettyResponse response = RpcClient.send(request);
        System.out.println(response.getContent());
    }

    @Test
    public void testDemo1() {
       NettyRequest request = new NettyRequest();
        Order order = new Order(1L, "XS001");
       request.setContent(order);
       request.setCmd("com.zxj.netty.sale.controller.OrderController.saveOrder");

        NettyResponse response = RpcClient.send(request);
        System.out.println(JSONObject.toJSONString(response));
    }

    @Test
    public void testDemo2() {
        NettyRequest request = new NettyRequest();
        Order order = new Order(1L, "XS001");
        Order order2 = new Order(2L, "XS002");
        request.setContent(Lists.newArrayList(order, order2));
        request.setCmd("com.zxj.netty.sale.controller.OrderController.saveOrders");

        NettyResponse response = RpcClient.send(request);
        System.out.println(JSONObject.toJSONString(response));
    }

}
