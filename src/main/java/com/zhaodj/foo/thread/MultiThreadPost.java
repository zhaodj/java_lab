package com.zhaodj.foo.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.message.BasicNameValuePair;

import com.zhaodj.foo.http.HttpUtil;

public class MultiThreadPost {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Future<String> future1 = HttpUtil.postAsyn("http://localhost:9090/shop/buy.do", 
				new BasicNameValuePair("itemId", "12"),
				new BasicNameValuePair("quantity", "1"),
				new BasicNameValuePair("unit", "m"));
		Future<String> future2 = HttpUtil.postAsyn("http://localhost:9090/shop/buy.do", 
				new BasicNameValuePair("itemId", "12"),
				new BasicNameValuePair("quantity", "1"),
				new BasicNameValuePair("unit", "m"));
		Future<String> future3 = HttpUtil.postAsyn("http://localhost:9090/shop/buy.do", 
				new BasicNameValuePair("itemId", "12"),
				new BasicNameValuePair("quantity", "1"),
				new BasicNameValuePair("unit", "m"));
		System.out.println("=====================");
		System.out.println(future1.get());
		System.out.println(future2.get());
		System.out.println(future3.get());
	}

}
