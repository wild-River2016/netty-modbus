package com.lsh.handle;

import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.func.ModbusError;
import com.lsh.msg.ModbusMessage;
import com.lsh.msg.ReadHoldingRegistersResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import static com.lsh.constant.ModbusConstants.MBAP_LENGTH;

/**
 * @ClassName ModbusDecoder
 * @Description:
 * @Author lsh
 * @Date 2019/4/14 14:02
 * @Version
 */
public class ModbusDecoder extends ByteToMessageDecoder {

    private final boolean serverMode;

    public ModbusDecoder(boolean serverMode) {
        this.serverMode = serverMode;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.capacity() < MBAP_LENGTH + 1) {
            return;
        }
        ModbusHeader mbapHeader = ModbusHeader.decode(in);
        //功能码
        short functionCode = in.readUnsignedByte();

        ModbusMessage message = null;
        switch (functionCode) {
            case ModbusFunction.READ_HOLDING_REGISTERS:
                message = new ReadHoldingRegistersResponse();
        }

        if (ModbusFunction.isError(functionCode)) {
            message = new ModbusError(functionCode);
        } else if (message == null) {
            message = new ModbusError(functionCode, (short)1);
        }

        message.decode(in.readBytes(in.readableBytes()));

        ModbusFrame frame = new ModbusFrame(mbapHeader, message);

        out.add(frame);
    }
}
