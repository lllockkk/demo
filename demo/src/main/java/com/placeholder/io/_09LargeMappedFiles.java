package com.placeholder.io;

import java.io.*;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by L on 2017/8/19.
 */
public class _09LargeMappedFiles {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static int length = 0x8000000;

    public static void main(String[] args) throws IOException {
        FileChannel fc = new RandomAccessFile(filepath, "rw").getChannel();
        MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i=0; i<length; i++) {
            buffer.put((byte)'x');
        }
        for (int i=length/2; i<length/2 + 6; i++) {
            System.out.println((char)buffer.get(i));
        }
        fc.close();
    }
}
