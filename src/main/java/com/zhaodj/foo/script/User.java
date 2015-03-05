package com.zhaodj.foo.script;

import java.util.List;

public class User {
	
	private String name;
	private int age;
	private List<User> parents;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public List<User> getParents() {
		return parents;
	}
	public void setParents(List<User> parents) {
		this.parents = parents;
	}
	
	@Override
	public String toString() {
		return "User [" + (name != null ? "name=" + name + ", " : "")
				+ "age=" + age + "]";
	}

}
