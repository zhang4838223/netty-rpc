package com.zxj.netty.sale.service.impl;

import com.zxj.netty.annotation.RpcExpoter;
import com.zxj.netty.sale.model.Order;
import com.zxj.netty.sale.model.OrderListResponse;
import com.zxj.netty.sale.model.OrderResponse;
import com.zxj.netty.sale.service.OrderBiz;
import com.zxj.netty.sale.service.OrderService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@RpcExpoter
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderBiz orderBiz;

    @Override
    public OrderResponse saveOrder(Order order) {
        orderBiz.insert(order);
        OrderResponse response = new OrderResponse();
        response.setCode("0");
        response.setOrder(order);
        response.setText("处理成功！");
        return response;
    }

    @Override
    public OrderListResponse saveOrders(List<Order> orders) {
        orderBiz.insertBatch(orders);
        OrderListResponse response = new OrderListResponse();
        response.setCode("0");
        response.setOrders(orders);
        response.setText("处理成功！");
        return response;
    }
}
