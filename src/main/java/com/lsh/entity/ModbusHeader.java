package com.lsh.entity;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * @ClassName ModbusHeader
 * @Description: modbus头信息
 * @Author lsh
 * @Date 2019/4/9 20:54
 * @Version
 */
public class ModbusHeader {
    private final int transactionIdentifier;
    private final int protocolIdentifier;
    private final int length;
    private final short unitIdentifier;

    public ModbusHeader(int transactionIdentifier, int protocolIdentifier, int pduLength, short unitIdentifier) {
        this.transactionIdentifier = transactionIdentifier;
        this.protocolIdentifier = protocolIdentifier;
        this.length = pduLength + 1;
        this.unitIdentifier = unitIdentifier;
    }

    public int getTransactionIdentifier() {
        return transactionIdentifier;
    }

    public int getProtocolIdentifier() {
        return protocolIdentifier;
    }

    public int getLength() {
        return length;
    }

    public short getUnitIdentifier() {
        return unitIdentifier;
    }

    /**
     * 解码-将二进制流解码成java对象
     * @param buffer
     * @return
     */
    public static ModbusHeader decode(ByteBuf buffer) {
        return new ModbusHeader(buffer.readUnsignedShort(),
                    buffer.readUnsignedShort(),
                    buffer.readUnsignedShort(),
                    buffer.readUnsignedByte());
    }

    /**
     * 编码-封装成二进制
     * @return
     */
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer();
        buf.writeShort(transactionIdentifier);
        buf.writeShort(protocolIdentifier);
        buf.writeShort(length);
        buf.writeByte(unitIdentifier);
        return buf;
    }

    @Override
    public String toString() {
        return "ModbusHeader{" +
                "transactionIdentifier=" + transactionIdentifier +
                ", protocolIdentifier=" + protocolIdentifier +
                ", length=" + length +
                ", unitIdentifier=" + unitIdentifier +
                '}';
    }
}
