package com.lsh;

import com.lsh.entity.exception.ModbusTransportException;

import javax.swing.plaf.PanelUI;

/**
 * @ClassName modbus
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/20 19:16
 * @Version
 */
public class Modbus {
    //最大读取二进制数量
    public static final int DEFAULT_MAX_READ_BIT_COUNT = 2000;
    //最大读取寄存器地址数量
    public static final int DEFAULT_MAX_READ_REGISTER_COUNT = 125;
    //最大写寄存器地址数量
    public static final int DEFAULT_MAX_WRITE_REGISTER_COUNT = 120;

    private int maxReadBitCount = DEFAULT_MAX_READ_BIT_COUNT;
    private int maxReadRegisterCount = DEFAULT_MAX_READ_REGISTER_COUNT;
    private int maxWriteRegisterCount = DEFAULT_MAX_WRITE_REGISTER_COUNT;

    public void validateNumberOfRegisters(int registers) throws ModbusTransportException {
        if (registers < 1 || registers > maxReadRegisterCount) {
            throw new ModbusTransportException("Invalid number of registers: " + registers);
        }
    }
}
