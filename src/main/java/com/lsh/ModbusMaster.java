package com.lsh;

import com.lsh.entity.exception.ModbusInitException;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.msg.ModbusRequest;

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

    public final void send(ModbusRequest request) throws ModbusTransportException {
        request.validate(this);
        sendImpl(request);
    }

    abstract public void sendImpl(ModbusRequest request) throws ModbusTransportException;
}
