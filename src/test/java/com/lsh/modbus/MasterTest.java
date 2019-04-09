package com.lsh.modbus;

import com.serotonin.modbus4j.*;
import com.serotonin.modbus4j.code.DataType;
import com.serotonin.modbus4j.exception.ErrorResponseException;
import com.serotonin.modbus4j.exception.IllegalDataAddressException;
import com.serotonin.modbus4j.exception.ModbusInitException;
import com.serotonin.modbus4j.exception.ModbusTransportException;
import com.serotonin.modbus4j.ip.IpParameters;
import com.serotonin.modbus4j.locator.BaseLocator;
import com.serotonin.modbus4j.msg.*;
import org.junit.Test;

import java.util.Arrays;

public class MasterTest {
	
    public static void main(String[] args) throws Exception {
    	
    		//TCP传输方式
    		ModbusMaster master = getTcpMaster();
    		//RTU传输方式
//        	ModbusMaster master = getRtuMaster();
        	//ASCII传输模式
//        	ModbusMaster master = getAsiiMaster();
//       	ModbusMaster master = modbusFactory.createUdpMaster(ipParameters);

        try {
        	//初始化
        	//设置超时时间
        	master.setTimeout(500);
        	//设置重连次数
        	master.setRetries(1);
            master.init();
            //设置从站ID
            int slaveId = 1;
            
            //测试校验
//        	Timer timer = new Timer();
//    		timer.schedule(new TimerTask() {
//    			
//    			@Override
//    			public void run() {
//    				//RTU
//    				ModbusMaster master = getRtuMaster();
//    				//ASCII
////    				ModbusMaster master = getAsiiMaster();
//    				try {
//    					master.init();
//    					readCoilTest(master, 1, 0, 10);
//    				} catch (ModbusInitException e) {
//    					e.printStackTrace();
//    				} finally {
//    					master.destroy();
//    				}
//    			}
//    		}, 100, 1000);
            
            //------------------读---------------
            //读取开关量状态   01
//            readCoilTest(master, slaveId, 0, 5);
            //读取外部设备输入开关量状态  02
//            readDiscreteInputTest(master, slaveId, 0, 10);
            //读取保持寄存器数据 03
            readHoldingRegistersTest(master, slaveId, 0, 10);
            //读取外围设备输入的数据 04
//            readInputRegistersTest(master, slaveId, 0, 10);
            //------------------写---------------
            //开关量写单个数据 05
//            writeCoilTest(master, slaveId, 9, true);
            //开关量写入多个数据 15
//            writeCoilsTest(master, slaveId, 0, new boolean[] {true, true, true, true, true});
            //写入单个模拟量数据 06
//            writeRegisterTest(master, slaveId, 0, 100);
            //写入多个模拟量数据 16
//            writeRegistersTest(master, slaveId, 0, new short[] {1, 10, 100, 1000, 10000, (short) 65534});

            //批量读取
//		       bathRead(master, slaveId);
            //根据类型写
		       writeValue(master);
		    //异常
//		       readExceptionStatusTest(master, slaveId);

        }
        finally {
            master.destroy();
        }
    }



	/**
	 * @Description: 根据类型写数据
	 * @param master
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException 
	 */
	public static void writeValue(ModbusMaster master) throws ModbusTransportException, ErrorResponseException {

//		BaseLocator<?> createLocator = StringLocator.createLocator(1, 1, DataType.TWO_BYTE_INT_UNSIGNED, 1, 2);
		
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_INT_UNSIGNED);
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_INT_UNSIGNED_SWAPPED);
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_INT_UNSIGNED);
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_INT_UNSIGNED);
		//测试高低位0x12345678
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_INT_UNSIGNED);
//		BaseLocator<Number> hr1 = BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED);
		BaseLocator<Number> hr1 = BaseLocator.inputRegister(1, 0, DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED);
//		BaseLocator<Number> hr2 = BaseLocator.holdingRegister(1, 0, DataType.TWO_BYTE_INT_UNSIGNED);
		//测试浮点数
//		BaseLocator<Number> hr2 = BaseLocator.holdingRegister(1, 0, DataType.FOUR_BYTE_INT_UNSIGNED);
//		master.setValue(hr2, 0x3F800000);
		
		 master.setValue(hr1, 0x12345678);
		 
		
	}

       

	/**
	 * @Description: 批量读取    可以批量读取不同寄存器中数据
	 * @param master
	 * @param slaveId
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException 
	 */
	public static void bathRead(ModbusMaster master, int slaveId)
			throws ModbusTransportException, ErrorResponseException {
		
		
		    BatchRead<Number> batch = new BatchRead<Number>();
		    
		    //****************批量读取不同寄存器中的单个数据********************
		    //读取线圈状态开关量
//		   	batch.addLocator(1, BaseLocator.coilStatus(slaveId, 0));
		   	//读取输入状态 开关量
//		   	batch.addLocator(2, BaseLocator.inputStatus(slaveId, 0));
		   
		   	//读取保持寄存器数据
		   	//根据设置的数据类型读取
//		   	batch.addLocator(3, BaseLocator.holdingRegister(slaveId, 0, DataType.TWO_BYTE_INT_UNSIGNED));
		   	//读取整形中16位中某一位的布尔值
//		   	batch.addLocator(3.1, BaseLocator.holdingRegisterBit(slaveId, 0, 0));
		   	
		   	//读取输入寄存器数据
		   	//根据设置的数据类型读取
//		   	batch.addLocator(4, BaseLocator.inputRegister(slaveId, 0, DataType.TWO_BYTE_INT_UNSIGNED));
		 	//读取整形中16位中某一位的布尔值
//		   	batch.addLocator(4.1, BaseLocator.inputRegisterBit(slaveId, 0, 0));
		   	
		   	//高低字节颠倒
		   	batch.addLocator(1, BaseLocator.holdingRegister(slaveId, 0, DataType.FOUR_BYTE_INT_UNSIGNED_SWAPPED));
		   	//高低字节不颠倒
//		   	batch.addLocator(1, BaseLocator.holdingRegister(slaveId, 0, DataType.FOUR_BYTE_INT_UNSIGNED));
		   	//读取浮点数
//		   	batch.addLocator(1, BaseLocator.holdingRegister(slaveId, 0, DataType.FOUR_BYTE_FLOAT));
			 BatchResults<Number> results = master.send(batch);
//			 System.out.println("批量读取1:--" + results.getValue(1));
//			 System.out.println("批量读取2:--" + results.getValue(2));
//			 System.out.println("批量读取3:--" + results.getValue(3));
//			 System.out.println("批量读取3.1:--" + results.getValue(3.1));
//			 System.out.println("批量读取3.2:--" + results.getValue(3.2));
//			 System.out.println("批量读取4:--" + results.getValue(4));
//			 System.out.println("批量读取4.1:--" + results.getValue(4.1));
			 //高低字节颠倒
			 System.out.println(Long.toHexString((long) results.getValue(1)));
			 
			 
	}


    
    /**
     * @Description: 读取开关量
     * @param master 主站实例 
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 读取的长度
     */
    public static void readCoilTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadCoilsRequest request = new ReadCoilsRequest(slaveId, start, len);
            ReadCoilsResponse response = (ReadCoilsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:1--" + Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 读取外围设备输入的开关量
     * @param master 主站实例
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 长度
     */
    public static void readDiscreteInputTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadDiscreteInputsRequest request = new ReadDiscreteInputsRequest(slaveId, start, len);
            ReadDiscreteInputsResponse response = (ReadDiscreteInputsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:2--" + Arrays.toString(response.getBooleanData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 读取保持寄存器数据
     * @param master 主站实例
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 长度
     */
    public static void readHoldingRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(slaveId, start, len);
            ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:3--" + Arrays.toString(response.getShortData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 读取外围设备输入的数据
     * @param master 主站实例
     * @param slaveId 从站ID
     * @param start 起始位
     * @param len 长度
     */
    public static void readInputRegistersTest(ModbusMaster master, int slaveId, int start, int len) {
        try {
            ReadInputRegistersRequest request = new ReadInputRegistersRequest(slaveId, start, len);
            ReadInputRegistersResponse response = (ReadInputRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:4--" + Arrays.toString(response.getShortData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 写开关量数据
     * @param master 
     * @param slaveId 从站ID
     * @param offset 偏移量
     * @param value 写入的值
     */
    public static void writeCoilTest(ModbusMaster master, int slaveId, int offset, boolean value) {
        try {
            WriteCoilRequest request = new WriteCoilRequest(slaveId, offset, value);
            WriteCoilResponse response = (WriteCoilResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:1,写入单个数据成功!");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 保持寄存器，写入单个数据
     * @param master
     * @param slaveId 从站ID
     * @param offset 偏移量
     * @param value 
     */
    public static void writeRegisterTest(ModbusMaster master, int slaveId, int offset, int value) {
        try {
            WriteRegisterRequest request = new WriteRegisterRequest(slaveId, offset, value);
            WriteRegisterResponse response = (WriteRegisterResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:3,写入单个模拟量数据成功!");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 读取异常状态
     * @param master
     * @param slaveId 
     */
    public static void readExceptionStatusTest(ModbusMaster master, int slaveId) {
        try {
            ReadExceptionStatusRequest request = new ReadExceptionStatusRequest(slaveId);
            ReadExceptionStatusResponse response = (ReadExceptionStatusResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(response.getExceptionStatus());
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void reportSlaveIdTest(ModbusMaster master, int slaveId) {
        try {
            ReportSlaveIdRequest request = new ReportSlaveIdRequest(slaveId);
            ReportSlaveIdResponse response = (ReportSlaveIdResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println(Arrays.toString(response.getData()));
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 保持寄存器写多个数据
     * @param master
     * @param slaveId 从站ID
     * @param start 起始位置
     * @param values 数值
     */
    public static void writeCoilsTest(ModbusMaster master, int slaveId, int start, boolean[] values) {
        try {
            WriteCoilsRequest request = new WriteCoilsRequest(slaveId, start, values);
            WriteCoilsResponse response = (WriteCoilsResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:1,写入多个数据成功!");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    /**
     * @Description: 保持寄存器写入多个模拟量数据
     * @param master
     * @param slaveId 从站ID
     * @param start modbus起始位置
     * @param values 数据
     */
    public static void writeRegistersTest(ModbusMaster master, int slaveId, int start, short[] values) {
        try {
            WriteRegistersRequest request = new WriteRegistersRequest(slaveId, start, values);
            WriteRegistersResponse response = (WriteRegistersResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("功能码:3,写入多个模拟量数据成功!");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }

    public static void writeMaskRegisterTest(ModbusMaster master, int slaveId, int offset, int and, int or) {
        try {
            WriteMaskRegisterRequest request = new WriteMaskRegisterRequest(slaveId, offset, and, or);
            WriteMaskRegisterResponse response = (WriteMaskRegisterResponse) master.send(request);

            if (response.isException())
                System.out.println("Exception response: message=" + response.getExceptionMessage());
            else
                System.out.println("Success");
        }
        catch (ModbusTransportException e) {
            e.printStackTrace();
        }
    }
    
    /**
	 * @return 
	 * @Description: 获取AsiiMaster
	 */
//	public static ModbusMaster getAsiiMaster(){
//		SerialParameters serialParameters = new SerialParameters();
//		// 设定MODBUS通讯的串行口
//		serialParameters.setCommPortId("COM4");
//		// 设置端口波特率
//		serialParameters.setBaudRate(9600);
//		//硬件之间输入流应答控制
//		serialParameters.setFlowControlIn(0);
//		//硬件之间输出流应答控制
//		serialParameters.setFlowControlOut(0);
//		//设定数据位的位数  RTU:8位    ASCII:7位
//		serialParameters.setDataBits(7);
//		//停止位的位数，如果无奇偶校验为2，有奇偶校验为1
//		serialParameters.setParity(2);
//		//停止位的位数，如果无奇偶校验为2，有奇偶校验为1
//		serialParameters.setStopBits(1);
//		// 设置端口名称
//		serialParameters.setPortOwnerName("ASCII");
//		// 创建ModbusMaster工厂实例
//		return new ModbusFactory().createAsciiMaster(serialParameters);
//	}
	
	/**
	 * @Description: 获取AsiiSalve
	 * @return 
	 */
//	public static ModbusSlaveSet getAsciiSalve(){
//		SerialParameters serialParameters = new SerialParameters();
//		// 设定MODBUS通讯的串行口
//		serialParameters.setCommPortId("COM4");
//		// 设置端口波特率
//		serialParameters.setBaudRate(9600);
//		//硬件之间输入流应答控制
//		serialParameters.setFlowControlIn(0);
//		//硬件之间输出流应答控制
//		serialParameters.setFlowControlOut(0);
//		//设定数据位的位数  RTU:8位    ASCII:7位
//		serialParameters.setDataBits(7);
//		//奇偶校验位  无校验：0 奇校验：1 偶校验：2
//		serialParameters.setParity(2);
//		//停止位的位数，如果无奇偶校验为2，有奇偶校验为1
//		serialParameters.setStopBits(1);
//		// 设置端口名称
//		serialParameters.setPortOwnerName("ASCII");
//		// 创建ModbusMaster工厂实例
//		return new ModbusFactory().createAsciiSlave(serialParameters);
//	}
	/**
	 * @return 
	 * @Description: 获取RTUMaster
	 */
//	public static ModbusMaster getRtuMaster(){
//		SerialParameters serialParameters = new SerialParameters();
//// 		TestSerialPortWrapper portWrapper = new TestSerialPortWrapper("COM4", 9600, 0, 0, 8, 1, 2);
//		// 设定MODBUS通讯的串行口
//		serialParameters.setCommPortId("COM4");
//		// 设置端口波特率
//		serialParameters.setBaudRate(9600);
//		//硬件之间输入流应答控制
//		serialParameters.setFlowControlIn(0);
//		//硬件之间输出流应答控制
//		serialParameters.setFlowControlOut(0);
//		//设定数据位的位数  RTU:8位    ASCII:7位
//		serialParameters.setDataBits(8);
//		//奇偶校验位  无校验：0 奇校验：1 偶校验：2
//		serialParameters.setParity(2);
//		//停止位的位数，如果无奇偶校验为2，有奇偶校验为1
//		serialParameters.setStopBits(1);
//		// 设置端口名称
//		serialParameters.setPortOwnerName("RTU");
//		// 创建ModbusMaster工厂实例
//		return new ModbusFactory().createRtuMaster(serialParameters);
//	}
    
	 
	
	/**
	 * @Description: 创建TcpMaster
	 * @return 
	 */
	public static ModbusMaster getTcpMaster() {
		IpParameters ipParameters = new IpParameters();
		//设置IP
		ipParameters.setHost("localhost");
		//设置端口 默认为502
		ipParameters.setPort(502);
		// 创建ModbusMaster工厂实例   
		return new ModbusFactory().createTcpMaster(ipParameters, true);
	}
	
	
	/**
	 * @Description: IO盒测试
	 * @throws ModbusInitException
	 * @throws ModbusTransportException
	 * @throws ErrorResponseException 
	 */
	@Test
	public void konnadTest() throws ModbusInitException, ModbusTransportException, ErrorResponseException{
		IpParameters params = new IpParameters();
		params.setHost("172.16.16.205");
		params.setPort(502);
		//获取主站
		ModbusMaster master = new ModbusFactory().createTcpMaster(params, false);
		master.init();
		//读取MAC地址
		ReadHoldingRegistersRequest request = new ReadHoldingRegistersRequest(1, 0, 3);
        ReadHoldingRegistersResponse response = (ReadHoldingRegistersResponse) master.send(request);
//        System.out.println("MAC地址" + ConversionUtil.getHexString(response.getData()));
        
//        WriteRegisterRequest request1 = new WriteRegisterRequest(1, 1056, 0);
//        ModbusResponse response1 = master.send(request1);
//        if (response1.isException())
//            System.out.println("Exception response: message=" + response1.getExceptionMessage());
//        else
//            System.out.println("功能码:3,写入多个模拟量数据成功!");
        //写数据 DO0的状态  0x0400  I/O盒实际为DO1
//        WriteRegisterRequest request1 = new WriteRegisterRequest(1, 1024, 1);
//        master.send(request1);
        //写数据 DO1上电的状态  0x0411 
//        WriteRegistersRequest request2 = new WriteRegistersRequest(1, 1040, new short[] {1, 1} );
//        master.send(request2);
        //读取 0x0411状态  为上电后的状态
//        ReadHoldingRegistersRequest request3 = new ReadHoldingRegistersRequest(1, 1040, 7);
//        ReadHoldingRegistersResponse response3 = (ReadHoldingRegistersResponse) master.send(request3);
//        System.out.println(Arrays.toString(response3.getShortData()));
		
	}
	
	/**
	 * @Description: 创建modbus服务端  从站
	 */
	@Test
	public void createSalve(){
		//创建modbus工厂
		ModbusFactory modbusFactory = new ModbusFactory();
		//创建TCP服务端
		final ModbusSlaveSet salve = modbusFactory.createTcpSlave(false);
		//创建ASCII服务端
//		final ModbusSlaveSet salve = getAsciiSalve();
		//向过程影像区添加数据
		salve.addProcessImage(getModscanProcessImage(1));
		
		 new Thread(new Runnable() {
	            @Override
	            public void run() {
	                try {
	                	salve.start();
	                }
	                catch (ModbusInitException e) {
	                    e.printStackTrace();
	                }
	            }
	        }).start();
		 
		 while (true) {
	            synchronized (salve) {
	            	try {
	            		salve.wait(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            }

	            for (ProcessImage processImage : salve.getProcessImages())
					try {
						updateProcessImage((BasicProcessImage) processImage);
					} catch (IllegalDataAddressException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        }
	}
	
	 /**
	 * @Description: 创建寄存器
	 * @param slaveId
	 * @return 
	 */
	public static BasicProcessImage getModscanProcessImage(int slaveId) {
		 BasicProcessImage processImage = new BasicProcessImage(slaveId);
		 processImage.setInvalidAddressValue(Short.MIN_VALUE);
		 
		 //创建可读写开关量区
		 processImage.setCoil(0, true);
		 processImage.setCoil(1, false);
		 processImage.setCoil(2, true);
		 processImage.setCoil(3, true);
		 processImage.setCoil(5, true);
		 processImage.setCoil(6, true);
		 processImage.setCoil(7, true);
		 processImage.setCoil(8, true);
		 processImage.setCoil(9, true);
		 
		 //创建只读开关量区
         processImage.setInput(0, false);
         processImage.setInput(1, false);
         processImage.setInput(2, true);
         processImage.setInput(3, false);
         processImage.setInput(4, true);
         processImage.setInput(5, true);
         processImage.setInput(6, true);
         processImage.setInput(7, true);
         processImage.setInput(8, true);
         processImage.setInput(9, true);
         
         //创建模拟量保持寄存器
         processImage.setHoldingRegister(0, (short) 1);
         processImage.setHoldingRegister(1, (short) 10);
         processImage.setHoldingRegister(2, (short) 100);
         processImage.setHoldingRegister(3, (short) 1000);
         processImage.setHoldingRegister(4, (short) 10000);
         processImage.setHoldingRegister(5, (short) 10000);
         processImage.setHoldingRegister(6, (short) 10000);
         processImage.setHoldingRegister(7, (short) 10000);
         processImage.setHoldingRegister(8, (short) 10000);
         processImage.setHoldingRegister(9, (short) 10000);
         
         //创建模拟量只读寄存器
         processImage.setInputRegister(0, (short) 10000);
         processImage.setInputRegister(1, (short) 1000);
         processImage.setInputRegister(2, (short) 100);
         processImage.setInputRegister(3, (short) 10);
         processImage.setInputRegister(4, (short) 1);
         processImage.setInputRegister(5, (short) 1);
         processImage.setInputRegister(6, (short) 1);
         processImage.setInputRegister(7, (short) 1);
         processImage.setInputRegister(8, (short) 1);
         processImage.setInputRegister(9, (short) 1);
         
         processImage.addListener(new BasicProcessImageListener());
         
		return processImage;
	 }
	
	/**
	 * @Description: 客户端修改本地寄存器的数据
	 * @author-lsh
	 * @date 2017年9月13日 下午5:47:30
	 */
	public static class BasicProcessImageListener implements ProcessImageListener {
	        @Override
	        public void coilWrite(int offset, boolean oldValue, boolean newValue) {
	            System.out.println("Coil at " + offset + " was set from " + oldValue + " to " + newValue);
	        }

	        @Override
	        public void holdingRegisterWrite(int offset, short oldValue, short newValue) {
	            // Add a small delay to the processing.
	            //            try {
	            //                Thread.sleep(500);
	            //            }
	            //            catch (InterruptedException e) {
	            //                // no op
	            //            }
	            System.out.println("HR at " + offset + " was set from " + oldValue + " to " + newValue);
	        }
	    }
	
	 /**
	 * @Description: 更新寄存器的数据
	 * @param processImage
	 * @throws IllegalDataAddressException 
	 */
	static void updateProcessImage(BasicProcessImage processImage) throws IllegalDataAddressException {
	        processImage.setInput(0, !processImage.getInput(0));
	        processImage.setInput(1, !processImage.getInput(1));

//	        processImage.setNumeric(RegisterRange.INPUT_REGISTER, 20, DataType.FOUR_BYTE_FLOAT, ir1Value += 0.01);
	//
//	        short hr1Value = processImage.getNumeric(RegisterRange.HOLDING_REGISTER, 80, DataType.TWO_BYTE_BCD)
//	                .shortValue();
//	        processImage.setNumeric(RegisterRange.HOLDING_REGISTER, 80, DataType.TWO_BYTE_BCD, hr1Value + 1);
	    }
	@Test
	public void StringTest(){
		String a = "abc123456789";
		char[] charArray = a.toCharArray();
		System.err.println(Arrays.toString(charArray));
	}
}
