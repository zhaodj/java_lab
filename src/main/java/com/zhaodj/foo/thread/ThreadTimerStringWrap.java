package com.zhaodj.foo.thread;

/**
 * Created by zhaodaojun on 2017/1/8.
 */
public class ThreadTimerStringWrap {

    private long start;

    public ThreadTimerStringWrap(){
        this.start = System.currentTimeMillis();
    }

    public String wrapSecond(String msg){
        return (System.currentTimeMillis() - start) / 1000 + " " + Thread.currentThread().getName() + " " + msg;
    }

    public void printSecond(String msg){
        System.out.println(wrapSecond(msg));
    }

    public String wrap(String msg){
        return (System.currentTimeMillis() - start) + " " + Thread.currentThread().getName() + " " + msg;
    }

    public void print(String msg){
        System.out.println(wrap(msg));
    }

}
