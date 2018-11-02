package com.zxj.netty.model;

import java.util.concurrent.atomic.AtomicLong;

public class NettyRequest {

    // 用uuid设置
    private final long id;
    private Object content;

    private static final AtomicLong aid = new AtomicLong(1);
    /**
     * 客户端请求的类名.方法名
     */
    private String cmd;

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public NettyRequest() {
        id = aid.incrementAndGet();
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public long getId() {
        return id;
    }
}
