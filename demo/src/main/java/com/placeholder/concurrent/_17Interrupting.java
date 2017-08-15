package com.placeholder.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by L on 2017/8/7.
 * lock.lockInterruptibly 相比 lock.lock 可以在该线程阻塞的时候，调用interrupt方法中断线程，而后者不会中断线程
 */
public class _17Interrupting {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Blocked());
        t.start();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Issuing t.interrupt()");
        t.interrupt();
    }
}

class BlockedMutex {
    private final Lock lock = new ReentrantLock();

    public BlockedMutex() {
        lock.lock();
    }

    public void f() {
        try {
            lock.lockInterruptibly();
            System.out.println("lock acquired in f()");
        } catch (InterruptedException e) {
            System.out.println("interrupted from lock acquisition in f()");
        }
    }
}

class Blocked implements Runnable {
    BlockedMutex blocked = new BlockedMutex();

    public void run() {
        System.out.println("Waiting for f() in BlockedMutex");
        blocked.f();
        System.out.println("Broken out of blocked call");
    }
}
