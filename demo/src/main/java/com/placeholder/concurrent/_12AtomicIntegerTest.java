package com.placeholder.concurrent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by L on 2017/8/6.
 */
public class _12AtomicIntegerTest implements Runnable {
    private AtomicInteger ai = new AtomicInteger(0);

    public void evenIncrement() {
        ai.addAndGet(2);
    }

    public int getValue() {
        return ai.get();
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            public void run() {
                System.err.println("Aborting");
                System.exit(0);
            }
        }, 5000);
        ExecutorService executor = Executors.newCachedThreadPool();
        _12AtomicIntegerTest ait = new _12AtomicIntegerTest();
        executor.execute(ait);
        while (true) {
            int value = ait.getValue();
            if (value % 2 != 0) {
                System.out.println(value);
                System.exit(0);
            }
        }
    }
}
