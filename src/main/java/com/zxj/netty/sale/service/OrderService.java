package com.zxj.netty.sale.service;

import com.zxj.netty.sale.model.Order;
import com.zxj.netty.sale.model.OrderListResponse;
import com.zxj.netty.sale.model.OrderResponse;

import java.util.List;

public interface OrderService {

    public OrderResponse saveOrder(Order order);

    public OrderListResponse saveOrders(List<Order> orders);
}
