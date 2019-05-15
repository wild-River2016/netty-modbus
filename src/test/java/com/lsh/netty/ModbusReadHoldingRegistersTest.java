/*
 * Copyright 2012 modjn Project
 *
 * The modjn Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.lsh.netty;


import com.lsh.client.ModbusClient;
import com.lsh.entity.exception.ConnectionException;
import com.lsh.entity.exception.ErrorResponseException;
import com.lsh.entity.exception.NoResponseException;
import com.lsh.example.ClientForTests;
import com.lsh.msg.ReadHoldingRegistersResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 *
 * @author Andreas Gabriel <ag.gandev@googlemail.com>
 */
public class ModbusReadHoldingRegistersTest {

    ModbusClient modbusClient;

    @Before
    public void setUp() throws Exception {
        modbusClient = ClientForTests.getInstance().getModbusClient();
    }

    @Test
    public void testReadHoldingRegisters() throws NoResponseException, ErrorResponseException, ConnectionException {
//        ReadHoldingRegistersResponse readHoldingRegisters = modbusClient.readHoldingRegisters(0, 10);
//
//        assertNotNull(readHoldingRegisters);
//        System.out.println(readHoldingRegisters);
    }
}
