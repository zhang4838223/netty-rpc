package com.zxj.netty.sale.service.impl;

import com.zxj.netty.sale.model.Order;
import com.zxj.netty.sale.service.OrderBiz;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBizImpl implements OrderBiz {
    @Override
    public int insert(Order order) {
        return 0;
    }

    @Override
    public int insertBatch(List<Order> orderList) {
        return 0;
    }
}
