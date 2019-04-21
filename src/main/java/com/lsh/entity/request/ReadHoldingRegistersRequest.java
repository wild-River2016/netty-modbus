package com.lsh.entity.request;

import com.lsh.entity.func.AbstractFunction;

/**
 * @ClassName ReadHoldingRegistersRequest
 * @Description: 保持寄存器-请求
 * @Author lsh
 * @Date 2019/4/14 18:17
 * @Version
 */
public class ReadHoldingRegistersRequest extends AbstractFunction {

    public ReadHoldingRegistersRequest(short functionCode) {
        super(READ_HOLDING_REGISTERS);
    }

    /**
     * @param address 0x0000 to 0xFFFF
     * @param quantity 1 - 125
     */
    public ReadHoldingRegistersRequest(int address, int quantity) {
        super(READ_HOLDING_REGISTERS, address, quantity);
    }

    public int getStartingAddress() {
        return address;
    }

    public int getQuantityOfInputRegisters() {
        return value;
    }

    @Override
    public String toString() {
        return "ReadHoldingRegistersRequest{" +
                "startingAddress=" + address +
                ", quantityOfInputRegisters=" + value +
                '}';
    }


}
