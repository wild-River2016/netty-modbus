package com.lsh.client;

import com.lsh.constant.ModbusConstants;
import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFrame2;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.exception.ConnectionException;
import com.lsh.entity.exception.ErrorResponseException;
import com.lsh.entity.exception.NoResponseException;
import com.lsh.handle.ModbusChannelInitializer;
import com.lsh.handle.ModbusResponseHandler;
import com.lsh.msg.ModbusMessage;
import com.lsh.msg.ReadHoldingRegistersRequest;
import com.lsh.msg.ReadHoldingRegistersResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

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
    private int lastTransactionIdentifier = 0;
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

    public void setup() throws ConnectionException {
        setup(null);
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

    public ReadHoldingRegistersResponse readHoldingRegisters(int startAddress, int quantityOfInputRegisters) throws ConnectionException, ErrorResponseException, NoResponseException {

        return this.<ReadHoldingRegistersResponse>callModbusFunctionSync(new ReadHoldingRegistersRequest(startAddress, quantityOfInputRegisters));
    }

    public <V extends ModbusMessage> V callModbusFunctionSync(ModbusMessage modbusMessage) throws ConnectionException, ErrorResponseException, NoResponseException {
        //获取事务处理标识
        int transactionId = callModbusFunction(modbusMessage);
        ModbusResponseHandler handler =(ModbusResponseHandler) channel.pipeline().get("responseHandler");
        if (handler == null) {
            throw new ConnectionException("Not connected!");
        }
        return (V)handler.getResponse(transactionId).getMessage();
    }

    /**
     *
     * @param modbusMessage
     * @return
     * @throws ConnectionException
     */
    public int callModbusFunction(ModbusMessage modbusMessage) throws ConnectionException {
        if (channel == null) {
            throw new ConnectionException("Not connected!");
        }
        int transactionId = calculateTransactionIdentifier();
        //获取此功能码的pdu长度()
        int pduLength = modbusMessage.calculateLength();
        ModbusHeader header = new ModbusHeader(transactionId, protocolIdentifier, pduLength, unitIdentifier);
        ModbusFrame frame = new ModbusFrame(header, modbusMessage);
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
