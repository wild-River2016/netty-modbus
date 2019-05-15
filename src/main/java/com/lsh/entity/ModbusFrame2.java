package com.lsh.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ModbusFrame
 * @Description: modbus报文结构
 * @Author lsh
 * @Date 2019/4/9 20:51
 * @Version
 */
public class ModbusFrame2 {

    private final ModbusHeader header;
    private final ModbusFunction function;

    public ModbusFrame2(ModbusHeader header, ModbusFunction function) {
        this.header = header;
        this.function = function;
    }

    public ModbusHeader getHeader() {
        return header;
    }

    public ModbusFunction getFunction() {
        return function;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(header.encode());
        buf.writeBytes(function.encode());
        return buf;
    }

    @Override
    public String toString() {
        return "ModbusFrame{" +
                "header=" + header +
                ", function=" + function +
                '}';
    }
}
