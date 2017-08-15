package com.placeholder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/3.
 */
public class _06ThreadFactoryDemo {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool(new DaemonThreadFactory());
        for (int i=0; i<10; i++)
            executor.execute(new Runnable() {
                public void run() {
                    while(true) {
                        try {
                            TimeUnit.MILLISECONDS.sleep(100);
                            System.out.println(Thread.currentThread() + " " + this);
                        } catch (InterruptedException e) {
                            System.out.println("sleep() interrupt");
                        }
                    }
                }
            });
        System.out.println("all daemon threads start");
        TimeUnit.MILLISECONDS.sleep(100);
    }
}

class DaemonThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(t.getName() + "-daemon");
        t.setDaemon(true);
        return t;
    }
}
