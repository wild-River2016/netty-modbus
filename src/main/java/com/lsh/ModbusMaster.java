package com.lsh;

import com.lsh.entity.exception.ModbusInitException;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.msg.ModbusRequest;
import com.lsh.msg.ModbusResponse;

/**
 * @ClassName ModbusMaster
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 19:30
 * @Version
 */
abstract public class ModbusMaster extends Modbus{
    private int timeout = 500;
    private int retries = 2;

    abstract public void init() throws ModbusInitException;

    public final ModbusResponse send(ModbusRequest request) throws ModbusTransportException {
        request.validate(this);
        return sendImpl(request);
    }

    abstract public ModbusResponse sendImpl(ModbusRequest request) throws ModbusTransportException;
}
