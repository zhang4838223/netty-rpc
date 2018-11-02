package com.zxj.netty.server;

import com.alibaba.fastjson.JSONObject;
import com.zxj.netty.model.NettyResponse;
import com.zxj.netty.model.NettyServerRequest;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

public class SimpleServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        NettyServerRequest nettyServerRequest = JSONObject.parseObject(msg.toString(), NettyServerRequest.class);
        NettyResponse response = new NettyResponse();
        response.setId(nettyServerRequest.getId());
        response.setContent("is response");
        ctx.channel().writeAndFlush(JSONObject.toJSONString(response));
        ctx.channel().writeAndFlush("\r\n");
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent)evt;
            if (event.state().equals(IdleState.READER_IDLE)) {
                System.out.println("read idle!");
                ctx.channel().close();
            }

            if (event.state().equals(IdleState.WRITER_IDLE)) {
                System.out.println("write idle!");
            }

            if (event.state().equals(IdleState.ALL_IDLE)) {
                System.out.println("read and write idle!");
                ctx.channel().writeAndFlush("ping\r\n");
            }

        }
    }
}
