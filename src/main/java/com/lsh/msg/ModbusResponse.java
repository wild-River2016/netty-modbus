package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName ModbusResponse
 * @Description: modbus响应
 * @Author lsh
 * @Date 2019/4/22 22:19
 * @Version
 */
abstract public class ModbusResponse extends ModbusMessage {
    protected static final byte MAX_FUNCTION_CODE = (byte) 0x80;

    public ModbusResponse(short functionCode) {
        super(functionCode);
    }



}
