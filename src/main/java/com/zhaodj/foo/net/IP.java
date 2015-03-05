package com.zhaodj.foo.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IP {
	
	public static void main(String[] args) throws UnknownHostException {
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println(InetAddress.getLocalHost().toString());
		System.out.println(addr.getHostName());
	}

}
