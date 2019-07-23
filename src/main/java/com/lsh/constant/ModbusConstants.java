package com.lsh.constant;

/**
 * @ClassName ModbusConstants
 * @Description:
 * @Author lsh
 * @Date 2019/4/9 15:00
 * @Version
 */
public class ModbusConstants {

    public static final int ERROR_OFFSET = 0x80;

    public static final int SYNC_RESPONSE_TIMEOUT = 2000; //milliseconds
    public static final int TRANSACTION_IDENTIFIER_MAX = 100; //affects memory usage of library

    public static final int ADU_MAX_LENGTH = 260;
    public static final int MBAP_LENGTH = 7;
    //默认端口
    public static final int DEFAULT_MODBUS_PORT = 502;
    //默认协议标识(0:modbus协议)
    public static final short DEFAULT_PROTOCOL_IDENTIFIER = 0;
    //默认单元标识，即从设备地址(0-0xff)
    public static final short DEFAULT_UNIT_IDENTIFIER = 255;
}
