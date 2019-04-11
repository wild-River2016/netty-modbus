package com.lsh.handle;

import com.lsh.entity.ModbusFrame;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

/**
 * @ClassName ModbusEncoder
 * @Description: 编码操作-转换成二进制流
 * @Author lsh
 * @Date 2019/4/10 10:37
 * @Version
 */
public class ModbusEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(msg instanceof ModbusFrame) {
            ModbusFrame modbusFrame = (ModbusFrame) msg;
            ctx.writeAndFlush(modbusFrame.encode());
        } else {
            ctx.writeAndFlush(msg);
        }
    }
}
