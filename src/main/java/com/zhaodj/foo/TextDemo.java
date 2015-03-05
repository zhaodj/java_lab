package com.zhaodj.foo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class TextDemo {
	
	private static String intersectPlatforms(String[] syncPlatforms,String bindedPlatforms){
    	if(syncPlatforms!=null){
    		if(bindedPlatforms!=null){
    			String[] arrBinded=bindedPlatforms.split(",");
    			List<String> la=new ArrayList<String>(Arrays.asList(syncPlatforms));
    			List<String> lb=Arrays.asList(arrBinded);
    			/*Iterator<String> ia=la.iterator();
    			while(ia.hasNext()){
    				String a=ia.next();
    				if(!lb.contains(a)){
    					ia.remove();
    				}
    			}*/
    			la.retainAll(lb);
    			return StringUtils.join(la,',');
    		}
    	}
    	return null;
    }
	
	public static void test() throws UnsupportedEncodingException{
		System.out.println("[花][蜡烛][花][蜡烛]".replace("[花]", ""));
		System.out.println(intersectPlatforms(null,null));
		System.out.println(intersectPlatforms(new String[]{"sina","tencent","sohu"},null));
		System.out.println(intersectPlatforms(null,"sina,tencent"));
		System.out.println(intersectPlatforms(new String[]{},"sina,tencent"));
		System.out.println(intersectPlatforms(new String[]{},""));
		System.out.println(intersectPlatforms(new String[]{"sina"},"sina,tencent"));
		System.out.println(intersectPlatforms(new String[]{"sina","sohu"},"sina,tencent"));
		System.out.println(intersectPlatforms(new String[]{"sina","sohu"},"sina,tencent,sohu"));
		System.out.println(URLDecoder.decode("%E6%B3%A2%E6%B3%A2%E9%B9%BF", "UTF-8"));
		System.out.println("5024108938736940288".equals(String.valueOf(5024108938736940288l)));
		String string = "阿娇发达👃";
		System.out.println(string);
		byte[] bs = string.getBytes("utf-8");
		string = new String(bs,"utf-8");
		System.out.println(string);
		System.out.println(string.length());
		for(int i = 0; i < string.length(); i++) {
			System.out.println(String.format("%x", (int)string.charAt(i)));
		}
	}
	
	public static void testControl(){
		String str = "‮123";
		System.out.println(str.replaceAll("\\p{C}", ""));
		System.out.println(str);
		str = "登记卡了房‣";
		System.out.println(str.replaceAll("\\p{C}", ""));
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		testControl();
	}

}
