package com.lsh.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName NettyClient
 * @Description: 客户端
 * @Author lsh
 * @Date 2019/4/8 20:16
 * @Version
 */
public class NettyClient {

    private static final int MAX_RETRY = 5;
    private static final String HOST = "127.0.0.1";
    private static final int PORT = 502;


    public static void client() {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {

                    }
                });

        }

        private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
            bootstrap.connect(host, port).addListener(new GenericFutureListener() {

                @Override
                public void operationComplete(Future future) throws Exception {
                   if (future.isSuccess()) {
                       System.out.println(new Date() + ": 连接成功……");
                       Channel channel = ((ChannelFuture) future).channel();
                   } else if (retry == 0) {
                       System.err.println("重试次数已用完，放弃连接！");
                   } else {
                        //第几次重连
                       int order = (MAX_RETRY - retry) + 1;
                       //本次重连的间隔
                       int delay = 1 << order;
                       System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                       bootstrap.config().group().schedule(new Runnable() {
                           @Override
                           public void run() {
                               connect(bootstrap, host, port, retry - 1);
                           }
                       }, delay, TimeUnit.SECONDS);
                   }
                }
            });
        }
}
