package com.zhaodj.foo;

import java.util.ArrayList;
import java.util.List;

public class CollectionGeneric {
	
	public interface Record{
		public String getName();
	}
	
	public static class RecordA implements Record{

		@Override
		public String getName() {
			return "A";
		}
		
		@Override
		public String toString(){
			return getName();
		}
		
	}
	
	public static class RecordB implements Record{

		@Override
		public String getName() {
			return "B";
		}
		
		@Override
		public String toString(){
			return getName();
		}
		
	}
	
	public static List<? extends Record> createList(String type){
		if("A".equalsIgnoreCase(type)){
			List<RecordA> result = new ArrayList<RecordA>();
			result.add(new RecordA());
			return result;
		}
		List<RecordB> result = new ArrayList<RecordB>();
		result.add(new RecordB());
		return result;
	}
	
	public static void main(String[] args){
		List<? extends Record> lista = createList("A");
		System.out.println(lista);
		List<? extends Record> listb = createList("B");
		System.out.println(listb);
	}

}
