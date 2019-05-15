package com.lsh.handle;

import com.lsh.constant.ModbusConstants;
import com.lsh.entity.ModbusFrame;
import com.lsh.entity.ModbusFrame2;
import com.lsh.entity.exception.ErrorResponseException;
import com.lsh.entity.exception.NoResponseException;
import com.lsh.entity.func.ModbusError;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @ClassName ModbusResponseHandler
 * @Description: modbus 返回数据处理器
 * @Author lsh
 * @Date 2019/4/9 21:57
 * @Version
 */
public abstract class ModbusResponseHandler extends SimpleChannelInboundHandler<ModbusFrame> {

    private static final Logger logger = Logger.getLogger(ModbusResponseHandler.class.getSimpleName());
    /**
     * key:事务元标识符 value:返回数据信息
     * 事务元标识符（2个字节）：用于事务处理配对。在响应中，MODBUS服务器复制请求的事务处理标识符。
     * 这里在以太网传输中存在一个问题，就是先发后至，我们可以利用这个事务处理
     */
    private final Map<Integer, ModbusFrame> responses = new HashMap<Integer, ModbusFrame>(ModbusConstants.TRANSACTION_IDENTIFIER_MAX);
    
    public ModbusFrame getResponse(int transactionIdentifier) throws NoResponseException, ErrorResponseException {
        //增加2s的超时时间
        long timeoutTime = System.currentTimeMillis() + ModbusConstants.SYNC_RESPONSE_TIMEOUT;
        ModbusFrame frame;
        do {
            frame = responses.get(transactionIdentifier);
        } while (frame == null && (timeoutTime - System.currentTimeMillis()) > 0);

        if (frame != null) {
            responses.remove(transactionIdentifier);
        }

        if (frame == null) {
            throw new NoResponseException();
        } else if (frame.getMessage() instanceof ModbusError) {
            //返回异常码
            throw new ErrorResponseException((ModbusError) frame.getMessage());
        }

        return frame;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.log(Level.SEVERE, cause.getLocalizedMessage());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ModbusFrame msg) throws Exception {
        responses.put(msg.getHeader().getTransactionIdentifier(), msg);
        newResponse(msg);
    }

    public abstract void newResponse(ModbusFrame frame);
}
