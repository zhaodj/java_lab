package com.zhaodj.foo.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhaodaojun on 2017/5/9.
 */
public class FutureCancel {

    public static void main(String[] arg) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<String> future = executorService.submit(() -> {
            try{
                System.out.println("start sleep");
                Thread.sleep(10000);
            } finally {
                System.out.println("finally");
            }
            return "finish";
        });

        Thread.sleep(1000);

        future.cancel(true);

        executorService.shutdown();
    }

}
