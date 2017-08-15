package com.placeholder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by L on 2017/8/6.
 */
public class _10AtomicityTest implements Runnable {
    private int value = 0;

    public synchronized int getValue() {
        return value;
    }

    public synchronized void evenIncrement() {
        value ++;
        value ++;
    }

    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        _10AtomicityTest at = new _10AtomicityTest();
        executor.execute(at);
        while (true) {
            int value = at.getValue();
            if (value % 2 != 0) {
                System.out.println(value);
                System.exit(0);
            }
        }

    }
}
