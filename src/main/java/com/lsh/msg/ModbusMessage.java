package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName ModbusMessage
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 22:03
 * @Version
 */
abstract public class ModbusMessage {

    private short functionCode;

    public ModbusMessage(short functionCode) {
        this.functionCode = functionCode;
    }

    public short getFunctionCode() {
        return functionCode;
    }

    abstract public void validate(Modbus modbus) throws ModbusTransportException;

    abstract public int calculateLength();

    abstract public ByteBuf encode();

    abstract public void decode(ByteBuf data);


}
