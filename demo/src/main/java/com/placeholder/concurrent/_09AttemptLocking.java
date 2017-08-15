package com.placeholder.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by L on 2017/8/6.
 */
public class _09AttemptLocking {
    private Lock lock = new ReentrantLock();

    public void untimed() {
        boolean captured = lock.tryLock();
        try {
            System.out.println("tryLock(): " + captured);
        } finally {
            if(captured)
                lock.unlock();
        }
    }

    public void timed() {
        boolean captured = false;
        try {
            captured = lock.tryLock(2, TimeUnit.SECONDS);
            System.out.println("lock.tryLock(2, TimeUnit.SECONDS): " + captured);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (captured)
                lock.unlock();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        final _09AttemptLocking al = new _09AttemptLocking();
        al.untimed();
        al.timed();
        new Thread() {
            { setDaemon(true); }
            public void run() {
                al.lock.lock();
                System.out.println("acquired");
            }
        }.start();
        TimeUnit.SECONDS.sleep(1);
        al.untimed();
        al.timed();
    }
}
