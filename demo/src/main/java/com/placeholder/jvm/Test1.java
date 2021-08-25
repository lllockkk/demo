package com.placeholder.jvm;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class Test1 {
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executor = Executors.newCachedThreadPool();
        Object lock = new Object();
        executor.execute(new Task(lock));
        executor.execute(new Task(lock));
        executor.execute(new Notifier(lock));

        executor.shutdown();
//        Test1 test1 = new Test1();
//        synchronized (test1) {
//            test1.notify();
//        }


//        System.out.println(new MyThread());
//        System.out.println(new MyThread());
    }
}

class Task implements Runnable {
    private Object lock;

    public Task(Object lock) {
        this.lock = lock;
    }
    @Override
    public void run() {
        try {
            synchronized (lock) {
                System.out.println(Thread.currentThread().getName() + " waiting");
                lock.wait();
                System.out.println("hello world");
                TimeUnit.SECONDS.sleep(2);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Notifier implements Runnable {
    private Object lock;

    public Notifier(Object lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            synchronized (lock) {
                TimeUnit.SECONDS.sleep(1);
                lock.notify();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}