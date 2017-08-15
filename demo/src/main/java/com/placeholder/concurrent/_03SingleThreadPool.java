package com.placeholder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by L on 2017/8/3.
 */
public class _03SingleThreadPool {
    public static void main(String[] args) {
        // 使用一个线程执行五个任务，相
        ExecutorService executor = Executors.newSingleThreadExecutor();
        for (int i=0; i<5; i++)
            executor.execute(new _01LiftOff());
        executor.shutdown();
    }
}
