package com.placeholder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * Created by L on 2017/8/5.
 */
public class _08CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool(new HandlerThreadFactory());
        HandlerThreadFactory factory = new HandlerThreadFactory();
        executor.execute(new ExceptionThread());
        executor.shutdown();
    }

}

class ExceptionThread implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        System.out.println("run() by " + t);
        System.out.println("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(Thread.currentThread() == t);
        System.out.println("caught " + e + ", " + t + " throwed");
    }
}

class HandlerThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(Runnable r) {
        System.out.println(this + " creating new Thread");
        Thread t = new Thread(r);
        System.out.println("created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        return t;
    }
}