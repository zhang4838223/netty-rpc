package com.zxj.netty.sale.model;

public class Order {

    private long id;
    private String code;

    public Order() {
    }

    public Order(long id, String code) {
        this.id = id;
        this.code = code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
