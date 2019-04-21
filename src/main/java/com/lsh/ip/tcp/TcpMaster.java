package com.lsh.ip.tcp;

import com.lsh.ModbusMaster;
import com.lsh.constant.ModbusConstants;
import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.exception.ConnectionException;
import com.lsh.entity.exception.ModbusInitException;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.handle.ModbusChannelInitializer;
import com.lsh.handle.ModbusResponseHandler;
import com.lsh.ip.IpParameters;
import com.lsh.msg.ModbusMessage;
import com.lsh.msg.ModbusRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.java.Log;

/**
 * @ClassName TcpMaster
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 16:34
 * @Version
 */
@Log
public class TcpMaster extends ModbusMaster {

    private final IpParameters ipParameters;
    private int lastTransactionIdentifier = 0;
    //初始化时连接：true 发送数据时连接：false
    private final boolean keepAlive;
    private Bootstrap bootstrap;
    private Channel channel;
    private ModbusResponseHandler handler;


    public TcpMaster(IpParameters ipParameters, boolean keepAlive) {
        this.ipParameters = ipParameters;
        this.keepAlive = keepAlive;
    }

    @Override
    public void init() throws ModbusInitException {
        init(null);
    }

    @Override
    public void sendImpl(ModbusRequest request) throws ModbusTransportException {
            try {
                if (!keepAlive) {
                    openConnection(handler);
                }
            } catch (ModbusInitException e) {
                e.printStackTrace();
            }




    }

    public void init(ModbusResponseHandler handler) throws ModbusInitException {
        this.handler = handler;
        try {
            if (keepAlive) {
                openConnection(handler);
            }
        } catch (Exception e) {
            throw new ModbusInitException(e);
        }

    }

    private void openConnection(ModbusResponseHandler handler) throws ModbusInitException {
        //先关闭连接
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ModbusChannelInitializer(handler));
        try {
            ChannelFuture f = bootstrap.connect(ipParameters.getHost(), ipParameters.getPort()).sync();
            channel = f.channel();
            channel.closeFuture().addListener(new GenericFutureListener<Future<? super Void>>() {
                @Override
                public void operationComplete(Future<? super Void> future) throws Exception {
                    workerGroup.shutdownGracefully();
                }
            });
        } catch (InterruptedException e) {
            throw new ModbusInitException(e.getLocalizedMessage());
        }
    }

    /**
     * 事务处理标识处理
     * @return
     */
    public synchronized int calculateTransactionIdentifier() {
        if (lastTransactionIdentifier < ModbusConstants.TRANSACTION_IDENTIFIER_MAX) {
            lastTransactionIdentifier ++;
        } else {
            lastTransactionIdentifier = 1;
        }
        return lastTransactionIdentifier;
    }

}
