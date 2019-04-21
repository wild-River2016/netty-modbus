package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.constant.ModbusConstants;
import com.lsh.entity.exception.ModbusTransportException;
import io.netty.buffer.ByteBuf;

/**
 * @ClassName ModbusRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 22:00
 * @Version
 */
abstract public class ModbusRequest extends ModbusMessage {


    public ModbusRequest(short functionCode) {
        super(functionCode);
    }

    public static boolean isError(short functionCode) {
        return functionCode - ModbusConstants.ERROR_OFFSET >= 0;
    }



}
