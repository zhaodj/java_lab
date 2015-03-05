package com.zhaodj.foo.math;

import java.util.Arrays;
import java.util.Random;

public class RandomSlice {
	
	public interface Slice{
		ChargeFree[] slice(double charge, double free, int len);
	}
	
	public static class ChargeFree{
		private double charge;
		private double free;
		public ChargeFree(){};
		public ChargeFree(double charge, double free){
			this.charge = charge;
			this.free = free;
		}
		public double getCharge() {
			return charge;
		}
		public void setCharge(double charge) {
			this.charge = charge;
		}
		public double getFree() {
			return free;
		}
		public void setFree(double free) {
			this.free = free;
		}
		@Override
		public String toString(){
			return "charge:" + charge + ",free:" + free;
		}
	}
	
	public static class SimpleSlice implements Slice{

		@Override
		public ChargeFree[] slice(double charge, double free, int len) {
			Random random = new Random();
			double t1 = charge;
			double t2 = free;
			ChargeFree[] res = new ChargeFree[len];
			for(int i = len; i > 0 ; i--){
				if(i == 1){
					res[i-1] = new ChargeFree(t1, t2);
				}else{
					res[i-1] = new ChargeFree(random.nextInt((int)(t1/i)), random.nextInt((int)(t2/i)));
				}
				t1 -= res[i-1].charge;
				t2 -= res[i-1].free;
			}
			return res;
		}
		
	}
	
	public static class ComplexSlice implements Slice{

		@Override
		public ChargeFree[] slice(double charge, double free, int len) {
			double base = 0.1d;
			double t = 0d;
			double[] rands = new double[len];
			for(int i = 0; i < len; i++){
				rands[i] = base + Math.random();
				t += rands[i];
			}
			double ratio1 = charge/t;
			double ratio2 = free/t;
			ChargeFree[] res = new ChargeFree[len];
			double total1 = charge;
			double total2 = free;
			for(int i = len; i > 0; i--){
				int tmp = (int)Math.round(rands[i-1]*ratio1);
				double c = (i == 1 ? total1 : tmp);
				total1 -= c;
				tmp = (int)Math.round(rands[i-1]*ratio2);
				double f = (i == 1 ? total2 : tmp);
				total2 -= f;
				res[i-1] = new ChargeFree(c, f);
			}
			int i = 0;
			while((res[i].getCharge() < 0 || res[i].getFree() < 0) && i < len - 1){
				if(res[i].getCharge() < 0){
					double tmp = res[i].getCharge();
					res[i].setCharge(0);
					res[i + 1].setCharge(tmp + res[i + 1].getCharge());
				}
				if(res[i].getFree() < 0){
					double tmp = res[i].getFree();
					res[i].setFree(0);
					res[i + 1].setFree(tmp + res[i + 1].getFree());
				}
				i++;
			}
			return res;
		}
		
	}
	
	public static void validate(double charge, double free, int len, Slice slice){
		ChargeFree[] res = slice.slice(charge, free, len);
		System.out.println(Arrays.toString(res));
		if(len != res.length){
			System.err.println("length error: " + charge + " " + free + ", " + len);
		}
		double t1 = 0, t2 = 0;
		boolean zero = false;
		for(ChargeFree i : res){
			t1 += i.getCharge();
			t2 += i.getFree();
			if(i.getCharge() <= 0 && i.getFree() <= 0){
				zero = true;
			}
		}
		if(zero){
			System.err.println("zero value error: " + charge + " " + free);
		}
		if(charge != t1 || free != t2){
			System.err.println("sum error: " + charge + " " + free + ", " + t1 + " " + t2);
		}
	}
	
	public static void main(String[] args){
		Slice ss = new SimpleSlice();
		ss = new ComplexSlice();
		double charge = 1000.7;
		double free = 100.3;
		int i = 100;
		while(i > 10){
			validate(charge, free, i, ss);
			i--;
		}
	}

}
