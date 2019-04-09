package com.lsh.client;

import com.lsh.constant.ModbusConstants;
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

    private final String host;
    private final int port;
    private short unitIdentifier;
    private short protocolIdentifier;


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


}
