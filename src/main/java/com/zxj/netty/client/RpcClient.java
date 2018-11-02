package com.zxj.netty.client;

import com.alibaba.fastjson.JSONObject;
import com.zxj.netty.common.RpcConstants;
import com.zxj.netty.model.DefaultFuture;
import com.zxj.netty.model.NettyRequest;
import com.zxj.netty.model.NettyResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class RpcClient {
    private static final Bootstrap bootstrap = new Bootstrap();
    private static ChannelFuture future = null;
    static {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        bootstrap.group(workGroup);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                // 二进制
                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(Integer.MAX_VALUE, Delimiters.lineDelimiter()[0]));
                // 字符串
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new SimpleClientHandle());
                socketChannel.pipeline().addLast(new StringEncoder());
            }
        });

        try {
            future = bootstrap.connect(RpcConstants.APP_IP, RpcConstants.PORT).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 多个请求使用相同链接，并发问题
    public static NettyResponse send(NettyRequest request) {
        future.channel().writeAndFlush(JSONObject.toJSONString(request));
        future.channel().writeAndFlush("\r\n");
        DefaultFuture defaultFuture = new DefaultFuture(request);
        return defaultFuture.get();
    }
}
