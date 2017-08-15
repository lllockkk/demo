package com.placeholder.concurrent;

import com.sun.org.apache.xpath.internal.WhitespaceStrippingElementMatcher;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/7.
 */
public class _14ThreadLocalVariableHolder {
    private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>() {
        private Random rand = new Random(47);

        @Override
        protected synchronized Integer initialValue() {
            return rand.nextInt(10000);
        }
    };

    public static void increment() {
        tl.set(tl.get() + 1);
    }

    public static int get() {
        return tl.get();
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0; i<10; i++)
            executor.execute(new Accessor(i));

        TimeUnit.SECONDS.sleep(3);
        executor.shutdownNow();
    }
}

class Accessor implements Runnable {
    private final int id;

    public Accessor(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            _14ThreadLocalVariableHolder.increment();
            System.out.println(this);
            Thread.yield();
        }
    }

    public String toString() {
        return "#" + id + ": " + _14ThreadLocalVariableHolder.get();
    }
}