package com.lsh.ip.tcp;

import com.lsh.ModbusMaster;
import com.lsh.constant.ModbusConstants;
import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFrame2;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.exception.*;
import com.lsh.handle.ModbusChannelInitializer;
import com.lsh.handle.ModbusResponseHandler;
import com.lsh.ip.IpParameters;
import com.lsh.msg.ModbusMessage;
import com.lsh.msg.ModbusRequest;
import com.lsh.msg.ModbusResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName TcpMaster
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 16:34
 * @Version
 */
@Slf4j
public class TcpMaster extends ModbusMaster {

    private final IpParameters ipParameters;
    private int lastTransactionIdentifier = 0;
    //初始化时连接：true 发送数据时连接：false
    private final boolean keepAlive;
    private Bootstrap bootstrap;
    private Channel channel;
    private ModbusResponseHandler handler;
    private short unitIdentifier;
    private short protocolIdentifier;


    @Override
    public void init() throws ModbusInitException {
        init(null);
    }

    public TcpMaster(IpParameters ipParameters, boolean keepAlive) {
        this.ipParameters = ipParameters;
        this.keepAlive = keepAlive;
    }

    @Override
    public ModbusResponse sendImpl(ModbusRequest request) throws ModbusTransportException {
            try {
                if (!keepAlive) {
                    openConnection(handler);
                }

                if(channel == null){
                    log.debug("Connection null: " +  ipParameters.getPort());
                }

            } catch (ModbusInitException e) {
                e.printStackTrace();
            }

        try {
            return callModbusMessageSync(request);
        } catch (ConnectionException e) {
            e.printStackTrace();
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (NoResponseException e) {
            e.printStackTrace();
        }
        return null;

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
        final NioEventLoopGroup workerGroup = new NioEventLoopGroup();
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

    public <V extends ModbusResponse> V callModbusMessageSync(ModbusMessage message) throws ConnectionException, ErrorResponseException, NoResponseException {
        //获取事务处理标识
        int transactionId = callModbusMessage(message);
        ModbusResponseHandler handler =(ModbusResponseHandler) channel.pipeline().get("responseHandler");
        if (handler == null) {
            throw new ConnectionException("Not connected!");
        }

        return (V)handler.getResponse(transactionId).getMessage();

    }

    public int callModbusMessage(ModbusMessage message) throws ConnectionException {
        if (channel == null) {
            throw new ConnectionException("Not connected!");
        }
        int transactionId = calculateTransactionIdentifier();
        //获取此功能码的pdu长度()
        int pduLength = message.calculateLength();
        ModbusHeader header = new ModbusHeader(transactionId, protocolIdentifier, pduLength, unitIdentifier);
        ModbusFrame frame = new ModbusFrame(header, message);
        channel.writeAndFlush(frame);
        return transactionId;
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
