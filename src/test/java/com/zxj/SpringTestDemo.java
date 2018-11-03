package com.zxj;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.zxj.netty.annotation.RpcReference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestDemo.class)
@ComponentScan("com.zxj")
public class SpringTestDemo {

    /**
     * 消费端引入远程服务
     */
//    @RpcReference
//    private OrderService orderService;
//
//    @Test
//    public void demo1() {
//        Order order = new Order(1L, "XS0001");
//        OrderResponse response = orderService.saveOrder(order);
//        System.out.println(JSONObject.toJSONString(response));
//    }
//
//    @Test
//    public void demo2() {
//        Order order = new Order(1L, "XS001");
//        Order order2 = new Order(2L, "XS002");
//
//        OrderListResponse response = orderService.saveOrders(Lists.newArrayList(order, order2));
//        System.out.println(JSONObject.toJSONString(response));
//
//    }
}
