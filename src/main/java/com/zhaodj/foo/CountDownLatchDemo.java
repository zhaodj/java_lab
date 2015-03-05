package com.zhaodj.foo;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    
    public static void main(String[] args){
        CountDownLatch latch=new CountDownLatch(1);
        latch.countDown();
        latch.countDown();
    }

}
