package com.lsh.entity.func;

import com.lsh.entity.ModbusFunction;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @ClassName AbstractFunction
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/14 18:33
 * @Version
 */
public class AbstractFunction extends ModbusFunction {

    protected int address;
    protected int value;

    public AbstractFunction(short functionCode) {
        super(functionCode);
    }

    public AbstractFunction(short functionCode, int address, int quantity) {
        super(functionCode);
        this.address = address;
        this.value = quantity;
    }

    /**
     * ++++++++++++++++++++++++++++++
     *  |功能码         |  1个字节    |
     *  |起始寄存器地址 |  2个字节    |
     *  |寄存器个数    |  2个字节    |
     *  ++++++++++++++++++++++++++++++
     * @return
     */
    @Override
    public int calculateLength() {
        return 1 + 2 + 2;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeShort(address);
        buf.writeShort(value);
        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        address = data.readUnsignedShort();
        value = data.readUnsignedShort();
    }
}
