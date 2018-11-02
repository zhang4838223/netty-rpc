package com.zxj.netty.sale.model;

import java.util.List;

public class OrderListResponse {

    private List<Order> orders;

    /**
     * 0表示成功
     */
    private String code = "0";
    private String text;

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
