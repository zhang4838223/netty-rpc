package com.zxj.netty.model;

public class NettyServerRequest {

    private Long id;
    /**
     * 请求入参
     */
    private Object content;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}
