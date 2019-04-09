package com.lsh.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Date;

/**
 * @ClassName HoldingRegisters
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/8 21:50
 * @Version
 */
public class HoldingRegisters extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println(new Date() + ": 客户端写出数据");

    }
}
