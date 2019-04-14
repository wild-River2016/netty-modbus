package com.lsh.handle;

import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFunction;
import com.lsh.entity.ModbusHeader;
import com.lsh.entity.func.ModbusError;
import com.lsh.entity.response.ReadHoldingRegistersResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.time.OffsetDateTime;
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

        ModbusFunction function = null;
        switch (functionCode) {
            case ModbusFunction.READ_HOLDING_REGISTERS:
                function = new ReadHoldingRegistersResponse();
        }

        if (ModbusFunction.isError(functionCode)) {
            function = new ModbusError(functionCode);
        } else if (function == null) {
            function = new ModbusError(functionCode, (short)1);
        }

        function.decode(in.readBytes(in.readableBytes()));

        ModbusFrame frame = new ModbusFrame(mbapHeader, function);

        out.add(frame);
    }
}
