package com.lsh.handle;

import com.lsh.constant.ModbusConstants;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @ClassName ModbusChannelInitializer
 * @Description: modbus channel初始化
 * @Author lsh
 * @Date 2019/4/9 16:07
 * @Version
 */
public class ModbusChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final SimpleChannelInboundHandler handler;

    public ModbusChannelInitializer(SimpleChannelInboundHandler handler) {
        this.handler = handler;
    }

    /**
     * Modbus TCP Frame Description
     *  - max. 260 Byte (ADU = 7 Byte MBAP + 253 Byte PDU)
     *  - Length field includes Unit Identifier + PDU
     *
     * <----------------------------------------------- ADU -------------------------------------------------------->
     * <---------------------------- MBAP -----------------------------------------><------------- PDU ------------->
     * +------------------------+---------------------+----------+-----------------++---------------+---------------+
     * | Transaction Identifier | Protocol Identifier | Length   | Unit Identifier || Function Code | Data          |
     * | (2 Byte)               | (2 Byte)            | (2 Byte) | (1 Byte)        || (1 Byte)      | (1 - 252 Byte |
     * +------------------------+---------------------+----------+-----------------++---------------+---------------+
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //数据包的最大长度、长度域的偏移量、长度域的长度
        pipeline.addLast("framer", new LengthFieldBasedFrameDecoder(ModbusConstants.ADU_MAX_LENGTH, 4, 2));


    }
}
