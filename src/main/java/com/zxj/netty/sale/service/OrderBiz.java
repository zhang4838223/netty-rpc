package com.zxj.netty.sale.service;

import com.zxj.netty.sale.model.Order;

import java.util.List;

public interface OrderBiz {

    public int insert(Order order);

    public int insertBatch(List<Order> orderList);
}
