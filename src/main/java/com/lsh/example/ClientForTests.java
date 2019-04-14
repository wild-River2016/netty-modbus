package com.lsh.example;



import com.lsh.client.ModbusClient;
import com.lsh.entity.exception.ConnectionException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ares
 */
public class ClientForTests {

    private final ModbusClient modbusClient;

    private ClientForTests() {
        modbusClient = new ModbusClient("127.0.0.1" /*
                 * "192.168.1.55"
                 */, 502); //ModbusConstants.MODBUS_DEFAULT_PORT);

        try {
            modbusClient.setup();
        } catch (ConnectionException ex) {
            Logger.getLogger(ClientForTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ModbusClient getModbusClient() {
        return modbusClient;
    }

    public static ClientForTests getInstance() {
        return ClientAndServerHolder.INSTANCE;
    }

    private static class ClientAndServerHolder {

        private static final ClientForTests INSTANCE = new ClientForTests();
    }
}
