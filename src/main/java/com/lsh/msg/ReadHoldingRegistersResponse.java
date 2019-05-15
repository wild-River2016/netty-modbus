package com.lsh.msg;

import com.lsh.Modbus;
import com.lsh.code.FunctionCode;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.msg.ModbusMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Arrays;

/**
 * @ClassName ReadHoldingRegisterResponse
 * @Description: 保持寄存器响应数据处理
 * @Author lsh
 * @Date 2019/4/14 14:40
 * @Version
 */
public class ReadHoldingRegistersResponse extends ModbusResponse {

    private short byteCount;
    /**
     * 寄存器数据
     */
    private int[] registers;

    public ReadHoldingRegistersResponse() {
        super(FunctionCode.READ_HOLDING_REGISTERS);
    }

    public ReadHoldingRegistersResponse(int[] registers) {
        super(FunctionCode.READ_HOLDING_REGISTERS);
        if (registers.length > 125) {
            throw new IllegalArgumentException();
        }
        this.byteCount = (short)(registers.length * 2);
        this.registers = registers;
    }

    public short getByteCount() {
        return byteCount;
    }

    public int[] getRegisters() {
        return registers;
    }

    @Override
    public void validate(Modbus modbus) throws ModbusTransportException {

    }

    /**
     *
     * =======================
     * |功能码     | 1个字节  |
     * |数据长度   | 1个字节  |
     * |寄存器数据 | n个字节  |
     * =======================
     * @return
     */
    @Override
    public int calculateLength() {
        return 1 + 1 + byteCount;
    }

    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(byteCount);
        for (int i = 0; i < registers.length; i++) {
            buf.writeShort(registers[i]);
        }
        return buf;
    }

    @Override
    public void decode(ByteBuf data) {
        //数据长度
        byteCount = data.readUnsignedByte();
        //寄存器数据(数据长度/2 字节，每个数据高字节在前)
        registers = new int[byteCount / 2];
        for (int i = 0; i < registers.length; i++) {
            registers[i] = data.readUnsignedShort();
        }
    }

    @Override
    public String toString() {
        StringBuilder registersStr = new StringBuilder();
        registersStr.append("{");
        for (int i = 0; i < registers.length; i++) {
            registersStr.append("register_")
                    .append(i)
                    .append("=")
                    .append(registers[i])
                    .append(", ");
        }
        registersStr.delete(registersStr.length() - 2, registersStr.length());
        registersStr.append("}");
        return "ReadHoldingRegistersResponse{" + "byteCount=" + byteCount + ", inputRegisters=" + registersStr + '}';
    }
}