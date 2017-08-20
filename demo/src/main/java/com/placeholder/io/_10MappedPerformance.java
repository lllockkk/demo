package com.placeholder.io;

import com.placeholder.Demo;

import java.io.*;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by L on 2017/8/19.
 */
public class _10MappedPerformance {
    private static String filepath = "C:/Users/L/Desktop/data.txt";
    private static int numOfInts = 4000000;
    private static int numOfUbufferInts = 200000;

    private abstract static class Tester {
        private String name;
        public Tester(String name) {
            this.name = name;
        }

        public void runTest() {
            try {
                System.out.print(name + ": ");
                long start = System.nanoTime();
                test();
                double duration = System.nanoTime() - start;
                System.out.format("%.2f\n", duration/1.0e9);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public abstract void test() throws IOException;
    }

    private static Tester[] testers = {
        new Tester("Stream Writer") {
            public void test() throws IOException {
                DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filepath)));
                for (int i=0; i<numOfInts; i++) {
                    dos.writeInt(i);
                }
                dos.close();
            }
        },
        new Tester("Mapped Writer") {
            public void test() throws IOException {
                FileChannel fc = new RandomAccessFile(filepath, "rw").getChannel();
                System.out.println("mw: " + fc.size());
                IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                for (int i=0; i<numOfInts; i++) {
                    ib.put(i);
                }
                fc.close();
            }
        },
        new Tester("Stream Read") {
            public void test() throws IOException {
                DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(filepath)));
                for (int i=0; i<numOfInts; i++) {
                    dis.readInt();
                }
                dis.close();
            }
        },
        new Tester("Mapped Read") {
            public void test() throws IOException {
                FileChannel fc = new FileInputStream(filepath).getChannel();
                IntBuffer ib = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size()).asIntBuffer();
                while (ib.hasRemaining()) {
                    ib.get();
                }
                fc.close();
            }
        },
        new Tester("Stream Read/Write") {
            public void test() throws IOException {
                RandomAccessFile raf = new RandomAccessFile(filepath, "rw");
                raf.writeInt(1);
                for (int i=1; i<numOfUbufferInts; i++) {
                    raf.seek(raf.length() - 4);
                    raf.writeInt(raf.readInt());
                }
                raf.close();
            }
        },
        new Tester("Mapped Read/Write") {
            public void test() throws IOException {
                FileChannel fc = new RandomAccessFile(filepath, "rw").getChannel();
                IntBuffer ib = fc.map(FileChannel.MapMode.READ_WRITE, 0, fc.size()).asIntBuffer();
                ib.put(0);
                for (int i=1; i<numOfUbufferInts; i++) {
                    ib.put(ib.get(i-1));
                }
                fc.close();
            }
        }
    };
    public static void main(String[] args) throws IOException {
        for (Tester tester : testers) {
            tester.runTest();
        }
    }
}
