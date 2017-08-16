package com.placeholder.io;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Created by L on 2017/8/16.
 */
public class _02RandomAccessFile {
    private static String filepath = "C:\\Users\\L\\Desktop\\b.txt";
    public static void dispaly() throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filepath, "r");
        for (int i=0; i<5; i++) {
            System.out.println(raf.readDouble());
        }
        raf.close();
    }
    public static void main(String[] args) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(filepath, "rw");
        for (int i=0; i<5; i++) {
            raf.writeDouble(i * 1.414);
        }
        raf.close();
        dispaly();
        raf = new RandomAccessFile(filepath, "rw");
        raf.seek(3 * 8);
        raf.writeDouble(1.111);
        raf.close();
        dispaly();
    }
}
