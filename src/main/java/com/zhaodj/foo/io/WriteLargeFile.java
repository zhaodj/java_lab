package com.zhaodj.foo.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by zhaodaojun on 2017/3/16.
 */
public class WriteLargeFile {

    private static void write(int lines) throws IOException {
        File file = File.createTempFile("foo_write", ".txt");
        FileWriter writer = new FileWriter(file);
        for(int i = 0; i < lines; i++){
            writer.write(i + "\n");
        }
        writer.flush();
        writer.close();
        System.out.println(file.getAbsolutePath());
    }

    private static void writeBuffered(int lines, int bufSize) throws IOException {
        File file = File.createTempFile("foo_write_buffered", ".txt");
        FileWriter writer = new FileWriter(file);
        BufferedWriter bufferedWriter = new BufferedWriter(writer, bufSize);
        for(int i = 0; i < lines; i++){
            bufferedWriter.write(i + "\n");
        }
        bufferedWriter.flush();
        bufferedWriter.close();
        System.out.println(file.getAbsolutePath());
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        int lines = 10000000;
        Thread.sleep(30000);
        long start = System.currentTimeMillis();
        writeBuffered(lines, 4096);
        System.out.println("cost: " + (System.currentTimeMillis() - start));
        Thread.sleep(30000);
    }

}
