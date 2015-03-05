package com.zhaodj.foo.math;

public class LineFunction {
	
	public static int calDecNum(double sum, double last, double x){
		double res = 2 * sum / last - 2 * sum / (last * last) * x;
		if(res <= 0){
			return 1;
		}
		return (int)Math.ceil(res);
	}
	
	public static void main(String[] args) {
		int s = 20;
		int last = 300;
		int sum = s;
		for(int i=0;i<last;i++){
			int res = calDecNum(s, last, i);
			if(sum <= 0){
				break;
			}
			if(sum - res < 0){
				res = 1;
			}
			sum -= res;
			System.out.println("cal: " + res);
		}
		System.out.println("last: " + sum);
	}

}
