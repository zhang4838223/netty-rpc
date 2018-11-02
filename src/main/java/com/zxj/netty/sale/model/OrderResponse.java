package com.zxj.netty.sale.model;

public class OrderResponse {

    private Order order;

    /**
     * 0表示成功
     */
    private String code = "0";
    private String text;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
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
