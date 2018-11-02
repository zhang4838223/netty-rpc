package com.zxj.netty.sale.controller;

import com.zxj.netty.sale.model.Order;
import com.zxj.netty.sale.model.OrderListResponse;
import com.zxj.netty.sale.model.OrderResponse;
import com.zxj.netty.sale.service.OrderService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class OrderController {

    @Resource
    private OrderService orderService;

    public OrderResponse saveOrder(Order order) {
        orderService.save(order);

        OrderResponse response = new OrderResponse();
        response.setCode("0");
        response.setOrder(order);
        response.setText("处理成功！");
        return response;

    }

    public OrderListResponse saveOrders(List<Order> orders) {
        orderService.saveOrders(orders);

        OrderListResponse response = new OrderListResponse();
        response.setCode("0");
        response.setOrders(orders);
        response.setText("处理成功！");
        return response;

    }
}
