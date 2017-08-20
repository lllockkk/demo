package com.placeholder.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * Created by L on 2017/8/19.
 */
public class _11LockingMappedFiles {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static int length = 0x8000000;
    private static FileChannel fc;

    public static void main(String[] args) throws Exception {
        fc = new RandomAccessFile(filepath, "rw").getChannel();
        MappedByteBuffer out = fc.map(FileChannel.MapMode.READ_WRITE, 0, length);
        for (int i=0; i<length; i++) {
            out.put((byte)'x');
        }
        new LockAndModify(out, 0, length/3);
        new LockAndModify(out, length/2, length/2 + length/4);
    }

    private static class LockAndModify extends Thread {
        private ByteBuffer buffer;
        private int start, end;

        LockAndModify(ByteBuffer bb, int start, int end) {
            this.start = start;
            this.end = end;
            bb.limit(end);
            bb.position(start);
            buffer = bb.slice();
            start();
        }

        public void run() {
            try {
                FileLock fl = fc.lock(start, end, false);
                while (buffer.position() < buffer.limit()-1) {
                    buffer.put((byte)(buffer.get()+1));
                }
                fl.release();
                System.out.println("Release: " + start + " to " + end);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
