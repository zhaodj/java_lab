package com.zhaodj.foo.performance;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ForTest {
	
	private List<Integer> list;
	
	private void setUp(int type, int size){
		if(type == 0){
			list = new ArrayList<Integer>(size);
		}else{
			list = new LinkedList<Integer>();
		}
		for(int i=0;i<size;i++){
			list.add(i);
		}
	}
	
	private void testForIter(int reps){
		for(int i=0;i<reps;i++){
			for(Integer val : list){
				
			}
		}
	}
	
	private void testForIndex(int reps){
		for(int i=0;i<reps;i++){
			for(int j=0;j<list.size();j++){
				list.get(j);
			}
		}
	}
	
	public static void main(String[] args) {
		int reps = 1000;
		ForTest test = new ForTest();
		test.setUp(0, 10000);
		long start = System.nanoTime();
		test.testForIter(reps);
		System.out.println("arraylist test iter: " + (System.nanoTime() - start));
		start = System.nanoTime();
		test.testForIndex(reps);
		System.out.println("arraylist test index: " + (System.nanoTime() - start));
		test.setUp(1, 10000);
		start = System.nanoTime();
		test.testForIter(reps);
		System.out.println("linkedlist test iter: " + (System.nanoTime() - start));
		start = System.nanoTime();
		test.testForIndex(reps);
		System.out.println("linkedlist test index: " + (System.nanoTime() - start));
	}

}
