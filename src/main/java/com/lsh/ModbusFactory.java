package com.lsh;

import com.lsh.ip.IpParameters;
import com.lsh.ip.tcp.TcpMaster;

/**
 * @ClassName ModbusFactory
 * @Description: TODO
 * @Author lsh
 * @Date 2019/4/13 17:07
 * @Version
 */
public class ModbusFactory {

    public ModbusMaster createTcpMaster(IpParameters params) {
        return new TcpMaster(params);
    }


}
