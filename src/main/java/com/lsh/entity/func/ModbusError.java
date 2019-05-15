package com.lsh.entity.func;

import com.lsh.Modbus;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.exception.ModbusTransportException;
import com.lsh.msg.ModbusMessage;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.HashMap;

/**
 * @ClassName ModbusError
 * @Description: modbus异常信息
 * @Author lsh
 * @Date 2019/4/13 14:43
 * @Version
 */
public class ModbusError extends ModbusMessage {

    /*
     * Modbus Exception Codes
     *
     * 01 ILLEGAL FUNCTION
     *
     * The function code received in the query is not an allowable action for
     * the server (or slave). This may be because the function code is only
     * applicable to newer devices, and was not implemented in the unit
     * selected. It could also indicate that the server (or slave) is in the
     * wrong state to process a request of this type, for example because it is
     * unconfigured and is being asked to return register values.
     *
     * 02 ILLEGAL DATA ADDRESS
     *
     * The data address received in the query is not an allowable address for
     * the server (or slave). More specifically, the combination of reference
     * number and transfer length is invalid. For a controller with 100
     * registers, the PDU addresses the first register as 0, and the last one as
     * 99. If a request is submitted with a starting register address of 96 and
     * a quantity of registers of 4, then this request will successfully operate
     * (address-wise at least) on registers 96, 97, 98, 99. If a request is
     * submitted with a starting register address of 96 and a quantity of
     * registers of 5, then this request will fail with Exception Code 0x02
     * “Illegal Data Address” since it attempts to operate on registers 96, 97,
     * 98, 99 and 100, and there is no register with address 100.
     *
     * 03 ILLEGAL DATA VALUE
     *
     * A value contained in the query data field is not an allowable value for
     * server (or slave). This indicates a fault in the structure of the
     * remainder of a complex request, such as that the implied length is
     * incorrect. It specifically does NOT mean that a data item submitted for
     * storage in a register has a value outside the expectation of the
     * application program, since the MODBUS protocol is unaware of the
     * significance of any particular value of any particular register.
     *
     * 04 SLAVE DEVICE FAILURE
     *
     * An unrecoverable error occurred while the server (or slave) was
     * attempting to perform the requested action.
     *
     * 05 ACKNOWLEDGE
     *
     * Specialized use in conjunction with programming commands. The server (or
     * slave) has accepted the request and is processing it, but a long duration
     * of time will be required to do so. This response is returned to prevent a
     * timeout error from occurring in the client (or master). The client (or
     * master) can next issue a Poll Program Complete message to determine if
     * processing is completed.
     *
     * 06 SLAVE DEVICE BUSY
     *
     * Specialized use in conjunction with programming commands. The server (or
     * slave) is engaged in processing a long–duration program command. The
     * client (or master) should retransmit the message later when the server
     * (or slave) is free.
     *
     * 08 MEMORY PARITY ERROR
     *
     * Specialized use in conjunction with function codes 20 and 21 and
     * reference type 6, to indicate that the extended file area failed to pass
     * a consistency check. The server (or slave) attempted to read record file,
     * but detected a parity error in the memory. The client (or master) can
     * retry the request, but service may be required on the server (or slave)
     * device.
     *
     * 0A GATEWAY PATH UNAVAILABLE
     *
     * Specialized use in conjunction with gateways, indicates that the gateway
     * was unable to allocate an internal communication path from the input port
     * to the output port for processing the request. Usually means that the
     * gateway is misconfigured or overloaded.
     *
     * 0B GATEWAY TARGET DEVICE FAILED TO RESPOND
     *
     * Specialized use in conjunction with gateways, indicates that no response
     * was obtained from the target device. Usually means that the device is not
     * present on the network.
     */
    /**
     * 存放异常功能码
     */
    private static final HashMap<Short, String> ERRORS = new HashMap<Short, String>();

    static {
        ERRORS.put((short) (0x01), "ILLEGAL FUNCTION");
        ERRORS.put((short) (0x02), "ILLEGAL DATA ADDRESS");
        ERRORS.put((short) (0x03), "ILLEGAL DATA VALUE");
        ERRORS.put((short) (0x04), "SLAVE DEVICE FAILURE");
        ERRORS.put((short) (0x05), "ACKNOWLEDGE");
        ERRORS.put((short) (0x06), "SLAVE DEVICE BUSY");
        ERRORS.put((short) (0x08), "MEMORY PARITY ERROR");
        ERRORS.put((short) (0x0A), "GATEWAY PATH UNAVAILABLE");
        ERRORS.put((short) (0x0B), "GATEWAY TARGET DEVICE FAILED TO RESPOND");
    }

    /**
     * 异常码
     */
    private short exceptionCode;
    /**
     * 异常信息
     */
    private String execptionMessage;

    public ModbusError(short functionCode) {
        super(functionCode);
    }

    @Override
    public void validate(Modbus modbus) throws ModbusTransportException {
        //TODO
    }

    public ModbusError(short functionCode, short exceptionCode) {
        super(functionCode);
        this.exceptionCode = exceptionCode;
    }

    public short getExecptionCode() {
        return exceptionCode;
    }

    public String getExecptionMessage() {
        return execptionMessage;
    }

    public void setExecptionMessage(short exceptionCode) {
        this.execptionMessage = ERRORS.get(exceptionCode) != null ? ERRORS.get(exceptionCode) : "UNDEFINED ERROR";
    }

    @Override
    public int calculateLength() {
        return 1 + 1;
    }

    /**
     * 编码:功能码+异常码
     * @return
     */
    @Override
    public ByteBuf encode() {
        ByteBuf buf = Unpooled.buffer(calculateLength());
        buf.writeByte(getFunctionCode());
        buf.writeByte(exceptionCode);
        return buf;
    }

    /**
     *  MODBUS异常响应：
     * 目的是为客户机提供与处理过程检测到的错误相关的信息
     * 响应功能码 = 请求功能码+0x80
     * @param data
     */
    @Override
    public void decode(ByteBuf data) {
        exceptionCode = data.readUnsignedByte();
        setExecptionMessage(exceptionCode);
    }

    @Override
    public String toString() {
        return "ModbusError{" +
                "exceptionCode=" + exceptionCode +
                ", execptionMessage='" + execptionMessage + '\'' +
                '}';
    }
}
