package com.zhaodj.foo.thread;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhaodaojun on 2017/1/8.
 */
public class MultiThreadWriteOneFile implements Runnable{

    private static ThreadTimerStringWrap tool = new ThreadTimerStringWrap();

    public static void main(String[] args){
        File file = new File("/Users/zhaodaojun/Downloads/" + MultiThreadWriteOneFile.class.getSimpleName() + ".txt");
        Thread thread1 = new Thread(new MultiThreadWriteOneFile(file));
        Thread thread2 = new Thread(new MultiThreadWriteOneFile(file));
        thread1.start();
        thread2.start();
    }

    private File file;

    public MultiThreadWriteOneFile(File file) {
        this.file = file;
    }

    @Override
    public void run() {
        try(FileWriter fw = new FileWriter(file, true)) {
            for (int i = 0; i < 10; i++) {
                tool.print(i + "");
                fw.write(tool.wrap(i + "\n"));
                Thread.sleep(100);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
