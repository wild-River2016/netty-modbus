package com.lsh.ip;

import com.lsh.base.ModbusUtils;

/**
 * @ClassName IpParameters
 * @Description: ip参数信息
 * @Author lsh
 * @Date 2019/4/18 14:19
 * @Version
 */
public class IpParameters {

    private String host;
    private int port = ModbusUtils.TCP_PORT;
    private boolean encapsulated;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }


    public void setPort(int port) {
        this.port = port;
    }

    public boolean isEncapsulated() {
        return encapsulated;
    }

    public void setEncapsulated(boolean encapsulated) {
        this.encapsulated = encapsulated;
    }
}
