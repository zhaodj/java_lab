package com.zhaodj.foo.thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhaodaojun on 2017/1/8.
 */
public class SleepOrWait {

    private static long startTime = System.currentTimeMillis();

    public static void main(String[] args) throws InterruptedException, IOException {
        String filepath = "/Users/zhaodaojun/Downloads/sleep_or_wait.txt";
        File file = new File(filepath);
        if (!file.exists()) {
            file.createNewFile();
        }
        //testSleep(filepath);
        testWait(file);
    }

    private static void println(String msg) {
        System.out.println(wrap(msg));
    }

    private static String wrap(String msg) {
        return (System.currentTimeMillis() - startTime) / 1000 + " " + Thread.currentThread().getName() + " " + msg;
    }

    private static void testSleep(String filepath) throws InterruptedException {
        Thread thread1 = new Thread(new SleepRunnable(filepath));
        Thread thread2 = new Thread(new SleepRunnable(filepath));
        thread1.start();
        Thread.sleep(5000);
        thread2.start();
    }

    private static void testWait(File file) throws InterruptedException {
        Thread thread1 = new Thread(new WaitRunnable(file));
        Thread thread2 = new Thread(new WaitRunnable(file));
        thread1.start();
        Thread.sleep(5000);
        thread2.start();
    }

    private static class SleepRunnable implements Runnable {

        private String filepath;
        private long count;

        public SleepRunnable(String filepath) {
            this.filepath = filepath;
        }

        @Override
        public void run() {
            println("start");
            while (true) {
                try (FileWriter fw = new FileWriter(filepath, true)) {
                    println(count + "");
                    fw.write(wrap(count + "\n"));
                    count++;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static class WaitRunnable implements Runnable {

        private File file;
        private long count;

        public WaitRunnable(File file) {
            this.file = file;
        }

        public void run() {
            println("start");
            while (true) {
                synchronized (file) {
                    try (FileWriter fw = new FileWriter(file, true)) {
                        println(count + "");
                        fw.write(wrap(count + "\n"));
                        count++;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        file.wait(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }

}
