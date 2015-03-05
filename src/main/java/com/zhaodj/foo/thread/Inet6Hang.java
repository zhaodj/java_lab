package com.zhaodj.foo.thread;

import java.net.InetAddress;

public class Inet6Hang {
	
	private static String[] hosts = new String[]{
		"h01.intra.bobo.163.com",
		"h02.intra.bobo.163.com",
		"h03.intra.bobo.163.com",
		"h04.intra.bobo.163.com",
		"h05.intra.bobo.163.com",
		"h06.intra.bobo.163.com"
	};
//	new String[]{
//			"h01.bobo.163.com",
//			"h02.bobo.163.com",
//			"h03.bobo.163.com",
//			"h04.bobo.163.com",
//			"h05.bobo.163.com",
//			"h06.bobo.163.com"
//		};
	
	public static void main(String[] args) {
		int threadNum = 10;
		for(int i=0;i<threadNum;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					boolean doing = true;
					while(doing){
						try {
							for(String host : hosts){
								InetAddress[] res = InetAddress.getAllByName(host);
								for(InetAddress add:res){
									System.out.println(add.toString());
								}
							}
							Thread.sleep(50);
						} catch (Exception e) {
							e.printStackTrace();
							doing = false;
						}
					}
				}},"inet6-"+i).start();
		}
	}

}
