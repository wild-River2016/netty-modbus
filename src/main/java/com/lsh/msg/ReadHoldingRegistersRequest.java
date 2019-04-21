package com.lsh.msg;

import com.lsh.code.FunctionCode;
import com.lsh.entity.exception.ModbusTransportException;

/**
 * @ClassName ReadHoldingRegistersRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 20:53
 * @Version
 */
public class ReadHoldingRegistersRequest extends ReadNumericRequest{

    public ReadHoldingRegistersRequest(short functionCode) {
        super(FunctionCode.READ_HOLDING_REGISTERS);
    }

    public ReadHoldingRegistersRequest(int startOffset, int numberOfRegisters) {
        super(FunctionCode.READ_HOLDING_REGISTERS, startOffset, numberOfRegisters);
    }

    @Override
    public String toString() {
        return "ReadHoldingRegistersRequest{" +
                "startOffset=" + startOffset +
                ", numberOfRegisters=" + numberOfRegisters +
                '}';
    }
}
