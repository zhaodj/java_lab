package com.zhaodj.foo.recur;

/**
 * Created by zhaodaojun on 16/4/4.
 */
public class TailRecur {

    public static long summary(long n){
        if(n == 1){
            return 1;
        }
        return n + summary(n - 1);
    }

    public static long tailSummary(long n, long sum){
        if(n == 0){
            return sum;
        }
        return tailSummary(n - 1, sum + n);
    }

    public static long iterSummary(long n){
        long sum = 0;
        for(long i = n; i > 0; i--){
            sum = sum + i;
        }
        return sum;
    }

    public static void main(String[] args){
        int n = 100000;
        System.out.println(iterSummary(n));

        try {
            System.out.println(summary(n));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        try {
            System.out.println(tailSummary(n, 0));
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
