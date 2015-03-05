package com.zhaodj.foo.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

public class Polling {
	
	private int index = 0;
	private String[] keys;
	private Map<String, String> map = new HashMap<String, String>();
	
	public Polling(){
		map.put("aaa", "a");
		map.put("bbb", "b");
		map.put("ccc", "c");
	}
	
	public String pollKey(){
		if(keys == null){
			synchronized(this){
				if(keys == null){
					keys = map.keySet().toArray(new String[]{});
				}
			}
		}
		int i = (index + 1)%keys.length;
		index = i;
		return keys[i];
	}
	
	public synchronized String syncPollKey(){
		if(keys == null){
			keys = map.keySet().toArray(new String[]{});
		}
		int i = (index + 1)%keys.length;
		index = i;
		return keys[i];
	}
	
	public static void main(String[] args) throws InterruptedException {
		long start = System.currentTimeMillis();
		int n = 100;
		final CountDownLatch doneSignal = new CountDownLatch(n);
		final Polling polling = new Polling();
		for(int i=0;i<n;i++){
			new Thread(new Runnable(){

				@Override
				public void run() {
					for(int j=0;j<10000;j++){
						//polling.pollKey();
						polling.syncPollKey();
						//System.out.println(Thread.currentThread().getName() + " " + j + " " + polling.pollKey());
					}
					doneSignal.countDown();
				}}).start();
		}
		doneSignal.await();
		System.out.println(System.currentTimeMillis()-start);
	}

}
