package com.zhaodj.foo;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

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

	public static void testEmoji() throws UnsupportedEncodingException{
		byte[] b1 = {-30,-102,-67};
		byte[] b2 = {-18,-128,-104};
		byte[] b3 = {-16,-97,-113,-128};
		byte[] b4 = {-18,-112,-86};
		String[] ios5emoji = new String[]{new String(b1,"utf-8"),new String(b3,"utf-8")};
		String[] ios4emoji = new String[]{new String(b2,"utf-8"),new String(b4,"utf-8")};
		System.out.println(Arrays.asList(ios5emoji));
		System.out.println(Arrays.asList(ios4emoji));
		byte[] testbytes = {105,111,115,-30,-102,-67,32,36,-18,-128,-104,32,36,-16,-97,-113,-128,32,36,-18,-112,-86};
		String tmpstr = new String(testbytes,"utf-8");
		System.out.println(tmpstr.length());
		for (int i=0;i<tmpstr.length();i++) {
			System.out.print(tmpstr.charAt(i));
			System.out.println(Character.isBmpCodePoint(tmpstr.codePointAt(i)));
		}
		System.out.println(tmpstr);
	}

	public static int parseIndex(String host){
		String[] arr = StringUtils.split(host, '-');
		String last = arr[arr.length - 1];
		String indexStr = last.substring(last.length() - 2);
		int index = StringUtils.isNumeric(indexStr) ? Integer.parseInt(indexStr) : new Random().nextInt(10);
		if(last.contains("staging")){
			index += 80;
		} else if(arr[0].equals("cq")){
			index += 40;
		}
		return index % 100;
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException{
	    //testControl();
		System.out.println(parseIndex("yf-zc-pos-cloud01"));
		System.out.println(parseIndex("cq-zc-pos-cloud01"));
		System.out.println(parseIndex("cq-zc-pos-staging01"));
		String str = "H2O\n" +
				"休闲吧";
		System.out.println(str.replaceAll("\\s", ""));
		System.out.println(Long.valueOf("2017-02-23".replaceAll("-", "").trim()));
		System.out.println("美团POS微信0515至0515 手续费0.00元 '阿宝家APO'S（威海路店）".replace("'", ""));
	}

}
