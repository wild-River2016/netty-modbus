package com.lsh.handle;

import com.lsh.entity.ModbusFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.logging.Logger;

/**
 * @ClassName ModbusResponseHandler
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/9 21:57
 * @Version
 */
public class ModbusResponseHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static final Logger logger = Logger.getLogger(ModbusResponseHandler.class.getSimpleName());

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame msg) throws Exception {

    }
}
