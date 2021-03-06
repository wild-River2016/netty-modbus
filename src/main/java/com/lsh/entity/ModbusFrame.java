package com.lsh.entity;

import com.lsh.msg.ModbusMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName ModbusFrame
 * @Description: modbus报文结构
 * @Author lsh
 * @Date 2019/4/22 10:28
 * @Version
 */
public class ModbusFrame {

    private final ModbusHeader header;
    private final ModbusMessage message;

    public ModbusFrame(ModbusHeader header, ModbusMessage message) {
        this.header = header;
        this.message = message;
    }

    public ModbusHeader getHeader() {
        return header;
    }

    public ModbusMessage getMessage() {
        return message;
    }

    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeBytes(header.encode());
        buf.writeBytes(message.encode());
        return buf;
    }

    @Override
    public String toString() {
        return "ModbusFrame{" +
                "header=" + header +
                ", message=" + message +
                '}';
    }
}
