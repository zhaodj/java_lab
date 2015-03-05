package com.zhaodj.foo.thread;

import java.util.Scanner;

public class ShutdownHookDemo {
	
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("shutdown");
			}
			
		}));
		
		Scanner sc = new Scanner(System.in);
		String line = null;
		while((line = sc.nextLine())!=null) {
			System.out.println(line);
		}
		
	}

}
