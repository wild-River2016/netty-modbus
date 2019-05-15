package com.lsh.msg;

/**
 * @ClassName ReadDiscreteInputsRequest
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/23 15:50
 * @Version
 */
public class ReadDiscreteInputsRequest extends ReadNumericRequest {


    public ReadDiscreteInputsRequest(short functionCode) {
        super(functionCode);
    }

    public ReadDiscreteInputsRequest(short functionCode, int startOffset, int numberOfRegisters) {
        super(functionCode, startOffset, numberOfRegisters);

    }
}
