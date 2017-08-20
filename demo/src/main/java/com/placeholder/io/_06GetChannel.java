package com.placeholder.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by L on 2017/8/18.
 */
public class _06GetChannel {
    private static final int BSIZE = 1024;

    public static void main(String[] args) throws IOException {
        String filepath = "C:/Users/L/Desktop/data.txt";
        FileChannel fc = new FileOutputStream(filepath).getChannel();
        fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        fc.close();
        fc = new FileInputStream(filepath).getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        fc.read(buffer);
        // The limit is set to the current position and then the position is set to zero.
        buffer.flip();
        while (buffer.hasRemaining())
            System.out.print((char)buffer.get());
    }
}
