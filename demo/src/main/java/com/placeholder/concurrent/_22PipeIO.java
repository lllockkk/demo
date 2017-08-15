package com.placeholder.concurrent;

import sun.text.resources.cldr.rm.FormatData_rm;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/8.
 */
public class _22PipeIO {
    public static void main(String[] args) throws IOException, InterruptedException {
        Sender sender = new Sender();
        Receiver receiver = new Receiver(sender);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(sender);
        executor.execute(receiver);
        TimeUnit.SECONDS.sleep(5);
        executor.shutdownNow();
    }
}

class Sender implements Runnable {
    private Random rand = new Random(47);
    private PipedWriter out = new PipedWriter();
    public PipedWriter getPipedWriter() {
        return out;
    }

    public void run() {
        try {
            while (true) {
                for (char c = 'A'; c <= 'Z'; c++) {
                    out.write(c);
                    TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                }
            }
        } catch (IOException e) {
            System.out.println(e + " Sender write exception");
        } catch (InterruptedException e) {
            System.out.println(e + " Sender sleep interrupted");
        }
    }
}

class Receiver implements Runnable {
    private PipedReader in;
    public Receiver(Sender sender) throws IOException {
        in = new PipedReader(sender.getPipedWriter());
    }
    public void run() {
        try {
            while (true) {
                System.out.print("Read: " + (char)in.read() + ", ");
            }
        } catch (IOException e) {
            System.out.println(e + " Receiver read exception");
        }
    }
}
