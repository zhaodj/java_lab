package com.zhaodj.foo;

public class EnumDemo {
	
	public enum Feature{
		BIG,STRONG;
		
		public static Feature getByString(String name){
			return Feature.valueOf(name.toUpperCase());
		}
	}
	
	public static void main(String[] args){
		System.out.println(Feature.getByString("big"));
		System.out.println(Feature.getByString("Big"));
		System.out.println(Feature.getByString("STRONG")==Feature.STRONG);
		System.out.println(Feature.BIG.name());
	}

}
