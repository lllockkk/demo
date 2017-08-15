package com.placeholder.concurrent;

import org.omg.CORBA.TIMEOUT;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/7.
 */
public class _15Interrupting {
    private static ExecutorService executor = Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        Future<?> f = executor.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        System.out.println("Interrupting " + r.getClass().getName());
        f.cancel(true);
        System.out.println("Interrupt sent to " + r.getClass().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());

        TimeUnit.SECONDS.sleep(3);
        System.out.println("All tasks completed? " + executor.isTerminated());
        System.out.println("Aborting with System.exit(0)");
        System.exit(0);
    }
}

class SleepBlocked implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(100);
        } catch (InterruptedException e) {
            System.out.println("InterruptedException");
        }
        System.out.println("Exiting SleepBlocked.run()");
    }
}

class IOBlocked implements Runnable {
    private InputStream is;

    public IOBlocked(InputStream is) {
        this.is = is;
    }

    @Override
    public void run() {
        try {
            System.out.println("Waiting for read():");
            is.read();
        } catch (IOException e) {
            if(Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted from block I/O");
            } else {
                throw new RuntimeException(e);
            }
            System.out.println("Exiting IOBlocked.run()");
        }
    }
}

class SynchronizedBlocked implements Runnable {

    public synchronized void f() {
        while (true)
            Thread.yield();
    }

    public SynchronizedBlocked() {
        new Thread(() -> f()).start();
    }

    @Override
    public void run() {
        System.out.println("Trying to call f()");
        f();
        System.out.println("Exiting SynchronizedBlocked.run()");
    }
}
