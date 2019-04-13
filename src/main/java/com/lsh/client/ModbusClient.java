package com.lsh.client;

import com.lsh.constant.ModbusConstants;
import com.lsh.entity.exception.ConnectionException;
import com.lsh.handle.ModbusChannelInitializer;
import com.lsh.handle.ModbusResponseHandler;
import com.serotonin.modbus4j.msg.ReadHoldingRegistersRequest;
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

import static com.lsh.constant.ModbusConstants.DEFAULT_PROTOCOL_IDENTIFIER;
import static com.lsh.constant.ModbusConstants.DEFAULT_UNIT_IDENTIFIER;

/**
 * @ClassName ModbusClient
 * @Description: 客户端
 * @Author lsh
 * @Date 2019/4/8 20:16
 * @Version
 */
public class ModbusClient {
    /**
     * 连接状态枚举类
     */
    public static enum CONNECTION_STATES {
        connected, notConnected, pending
    }
    

    private final String host;
    private final int port;
    private short unitIdentifier;
    private short protocolIdentifier;
    private Bootstrap bootstrap;
    private Channel channel;


    public ModbusClient(String host, int port) {
        this(host, port, DEFAULT_UNIT_IDENTIFIER, DEFAULT_PROTOCOL_IDENTIFIER);
    }

    public ModbusClient(String host, int port, short unitIdentifier) {
        this(host, port, unitIdentifier, DEFAULT_PROTOCOL_IDENTIFIER);
    }

    public ModbusClient(String host, int port, short unitIdentifier, short protocolIdentifier) {
        this.host = host;
        this.port = port;
        this.unitIdentifier = unitIdentifier;
        this.protocolIdentifier = protocolIdentifier;
    }

    public void setup(ModbusResponseHandler handler) throws ConnectionException {
       final NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ModbusChannelInitializer(handler));

        try {
            ChannelFuture f = bootstrap.connect(host, port).sync();
            channel = f.channel();
            channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    workerGroup.shutdownGracefully();
                }
            });
        } catch (InterruptedException e) {
            throw new ConnectionException(e.getLocalizedMessage());
        }
    }

    public void close() {
        if (channel != null) {
            channel.close().awaitUninterruptibly();
        }
    }


}
