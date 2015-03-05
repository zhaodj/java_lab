package com.zhaodj.foo.thread;

public class ThreadExceptionDemo {

		public static void main(String[] args){
			Thread thread=new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						Thread.sleep(5000);
						throw new RuntimeException("test error");
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}});
			
			thread.start();
			
			System.out.println("hello");
		}
	
}
