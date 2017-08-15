package com.placeholder.concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/3.
 */
public class _02CachedThreadPool {
    public static void main(String[] args) {
        // FixedThreadPool、SingleThreadPool
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0; i<5; i++)
            executor.execute(new _01LiftOff());

        executor.shutdown();
    }
}
