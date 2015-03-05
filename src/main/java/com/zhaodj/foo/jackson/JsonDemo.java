package com.zhaodj.foo.jackson;

import java.util.ArrayList;
import java.util.List;

public class JsonDemo {
	
	private String name;
	private int value;
	
	public JsonDemo(String name,int value) {
		this.name=name;
		this.value=value;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add(JSONUtil.toJson(new JsonDemo("a", 1)));
		list.add(JSONUtil.toJson(new JsonDemo("b", 2)));
		list.add(JSONUtil.toJson(new JsonDemo("c", 3)));
		System.out.println(JSONUtil.toJson(list));
	}

}
