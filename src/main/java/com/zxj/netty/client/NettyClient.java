package com.zxj.netty.client;

import com.zxj.netty.common.RpcConstants;
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
import io.netty.util.AttributeKey;

public class NettyClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
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

            ChannelFuture future = bootstrap.connect(RpcConstants.APP_IP, RpcConstants.PORT).sync();
            future.channel().writeAndFlush("hello netty! \r\n");
            future.channel().closeFuture().sync();
            // 等待关闭通道的时候执行
            Object result = future.channel().attr(AttributeKey.valueOf("sd")).get();

            System.out.println("server response:" + result.toString());
        } catch (InterruptedException e) {
            workGroup.shutdownGracefully();

            e.printStackTrace();
        }
    }
}
