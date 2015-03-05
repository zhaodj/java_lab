package com.zhaodj.foo.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AppConfig {
	
	private static AppConfig APP_CONFIG;
	
	private String url;
	
	public AppConfig() {
		APP_CONFIG = this;
	}
	
	public void setUrl(String url) {
		this.url=url;
	}
	
	public static String getUrl() {
		return APP_CONFIG.url;
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {"applicationContext.xml"});
		System.out.println(AppConfig.getUrl());
	}

}
