package com.zhaodj.foo.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by zhaodaojun on 2017/4/27.
 */
public class LeaderWorker {

    public static void main(String[] args){
        testSubmit();
    }

    private static void testSubmit(){
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        int taskNum = 50;
        List<Future<Integer>> futures = new ArrayList<>(taskNum);
        for(int i = 0; i < taskNum; i++){
            final int idx = i;
            futures.add(executorService.submit(() -> {
                System.out.println(Thread.currentThread().getName() + " start " + idx);
                Thread.sleep(new Random().nextInt(10000));
                if(idx % 2 == 0){
                    throw new RuntimeException("bad luck " + idx);
                }
                System.out.println(Thread.currentThread().getName() + " end " + idx);
                return idx;
            }));
        }
        for(Future<Integer> future : futures){
            try {
                System.out.println(future.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
                cancelAll(futures);
                break;
            } catch (ExecutionException e) {
                e.printStackTrace();
                cancelAll(futures);
                break;
            }
        }
        executorService.shutdown();
    }

    private static void cancelAll(List<Future<Integer>> futures){
        for(Future<Integer> future : futures){
            future.cancel(true);
        }
    }

    private static void testInvokeAll(){
        ExecutorService executorService = Executors.newFixedThreadPool(16);
        int taskNum = 50;
        List<Callable<Integer>> callableList = new ArrayList<>(taskNum);
        for(int i = 0; i < taskNum; i++){
            final int idx = i;
            callableList.add(() -> {
                System.out.println(Thread.currentThread().getName() + " start " + idx);
                Thread.sleep(new Random().nextInt(10000));
                if(idx % 2 == 0){
                    throw new RuntimeException("bad luck " + idx);
                }
                System.out.println(Thread.currentThread().getName() + " end " + idx);
                return idx;
            });
        }
        try {
            List<Future<Integer>> futures = executorService.invokeAll(callableList);
            for(Future<Integer> resFuture : futures){
                try {
                    System.out.println(resFuture.get());
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
