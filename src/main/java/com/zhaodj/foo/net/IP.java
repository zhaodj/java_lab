package com.zhaodj.foo.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IP {
	
	public static void main(String[] args) throws UnknownHostException, SocketException {
		InetAddress addr = InetAddress.getLocalHost();
		System.out.println(InetAddress.getLocalHost().toString());
		System.out.println(addr.getHostName());
		System.out.println(addr.getHostAddress());
		
		Enumeration<NetworkInterface> e=NetworkInterface.getNetworkInterfaces();
		while(e.hasMoreElements()){
		    NetworkInterface ni = e.nextElement();
		    System.out.println(ni.getDisplayName());
		    Enumeration<InetAddress> address = ni.getInetAddresses();
		    while(address.hasMoreElements()){
		        System.out.println(address.nextElement().getHostAddress());
		    }
		}
		
	}

}
