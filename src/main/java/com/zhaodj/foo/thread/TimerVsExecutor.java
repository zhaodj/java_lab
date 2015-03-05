package com.zhaodj.foo.thread;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TimerVsExecutor {
	
	ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
	
	public void exec(int num) {
		Random random = new Random();
		int delay = 0;
		for(int i = 0;i < num; i++) {
			delay = delay + 1 + random.nextInt(5);	
			executor.schedule(new Task(delay), delay, TimeUnit.SECONDS);
		}
	}
	
	public static class Task implements Runnable{

		private int delay;
		
		public Task(int delay) {
			this.delay = delay;
		}
		
		@Override
		public void run() {
			System.out.println(this.delay);
			if(this.delay % 5 == 0) {
				throw new RuntimeException("test");
			}
		}
		
	}
	
	public static void main(String[] args) {
		TimerVsExecutor timerVsExecutor = new TimerVsExecutor();
		timerVsExecutor.exec(10);
	}

}
