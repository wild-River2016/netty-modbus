package com.lsh.entity.exception;

import com.lsh.entity.func.ModbusError;

/**
 * @ClassName ErrorResponseException
 * @Description: 异常返回信息
 * @Author lsh
 * @Date 2019/4/13 16:03
 * @Version
 */
public class ErrorResponseException extends Exception{

    public ErrorResponseException(ModbusError modbusError) {
        super(modbusError.toString());
    }
}
