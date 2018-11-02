package com.zxj.netty.server;

import com.zxj.netty.common.RpcConstants;
import com.zxj.netty.zookeeper.ZookeeperFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import java.net.InetAddress;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class AppServer {

    public static void main( String[] args ) {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            serverBootstrap.group(parentGroup, childGroup);
            serverBootstrap.option(ChannelOption.SO_BACKLOG, 128)
                    .childOption(ChannelOption.SO_KEEPALIVE, false)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, Delimiters.lineDelimiter()));
                            socketChannel.pipeline().addLast(new StringDecoder());
                            socketChannel.pipeline().addLast(new IdleStateHandler(60, 45, 20, TimeUnit.SECONDS));
                            socketChannel.pipeline().addLast(new SimpleServerHandler());
                            socketChannel.pipeline().addLast(new StringEncoder());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(RpcConstants.PORT).sync();

            CuratorFramework client = ZookeeperFactory.create();
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath("/" + RpcConstants.APP_NAME + "/" + InetAddress.getLocalHost().getHostAddress());

            future.channel().closeFuture().sync();
        } catch (Exception e) {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
            e.printStackTrace();
        }
    }
}
