package com.zhaodj.foo.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import org.apache.commons.lang3.ArrayUtils;

public class UDPClient {

	private static final int PORT = 7000;
	private DatagramSocket dataSocket;
	private DatagramPacket dataPacket;
	private byte sendDataByte[];
	private String sendStr;

	public UDPClient() {
		init();
	}

	public void init() {
		try {
			// 指定端口号，避免与其他应用程序发生冲突

			dataSocket = new DatagramSocket(PORT + 1);
			sendDataByte = new byte[64];
			byte[] startByte = new byte[] { (byte) 0x88, (byte) 0x77, (byte) 0x66, (byte) 0x55, (byte) 0x01,
					(byte) 0x01 };
			byte[] validateByte = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			sendStr = "15210157146";
			byte[] temp = ArrayUtils.addAll(startByte, ArrayUtils.addAll(validateByte, sendStr.getBytes()));
			for (int i = 0; i < 64; i++) {
				if (i < temp.length) {
					sendDataByte[i] = temp[i];
				} else {
					sendDataByte[i] = (byte) 0x00;
				}
			}
			System.out.println(sendDataByte.length);
			dataPacket = new DatagramPacket(sendDataByte, sendDataByte.length, InetAddress.getByName("127.0.0.1"),
					PORT);
			dataSocket.send(dataPacket);
		} catch (SocketException se) {
			se.printStackTrace();
		} catch (IOException ie) {
			ie.printStackTrace();
		}
	}

	public static void main(String args[]) {
		new UDPClient();
	}

}