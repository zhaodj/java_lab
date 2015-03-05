package com.zhaodj.foo;

import java.net.MalformedURLException;
import java.net.URL;

public class UriDemo {
	
	private String domain = "http://app.56.com";
	
	public UriDemo(String domain){
		this.domain = domain;
	}
	
	public boolean validate(String url){
		if(url.equals(domain)){
			return true;
		}
		/*if(this.domain.endsWith("/")?url.startsWith(this.domain):url.startsWith(this.domain+"/")){
			return true;
		}*/
		try{
			URL du = new URL(this.domain);
			URL uu = new URL(url);
			if(du.getHost().equals(uu.getHost())&&url.startsWith(this.domain)){
				return true;
			}
		}catch(MalformedURLException e){
			return false;
		}
		return false;
	}
	
	public static void main(String[] args) throws MalformedURLException{
		String url="http://app.56.com";
		System.out.println("http://app.56.com@wooyun.org");
		URL u = new URL("http://app.56.com@wooyun.org");
		System.out.println(u.getHost());
		u = new URL("http://app.56.com/aaa.sinaapp.com");
		System.out.println(u.getHost());
	}

}
