package com.placeholder.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by L on 2017/8/7.
 */
public class _18WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        Car car = new Car();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new WaxOff(car));
        executor.execute(new WaxOn(car));
        TimeUnit.SECONDS.sleep(5);
        executor.shutdownNow();
    }
}

class Car2 {
    private boolean waxOn = false;

    public synchronized void waxOn() {
        waxOn = true;
        notifyAll();
    }

    public synchronized void waxOff() {
        waxOn = false;
        notifyAll();
    }

    public synchronized void waitForWaxOn() throws InterruptedException {
        if (!waxOn)
            wait();
    }
    public synchronized void waitForWaxOff() throws InterruptedException {
        if (waxOn)
            wait();
    }
}

class Car {
    private boolean waxOn = false;
    private Lock lock = new ReentrantLock();
    private Condition con = lock.newCondition();

    public void waxOn() {
        lock.lock();
        try {
            waxOn = true;
            con.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void waxOff() {
        lock.lock();
        try {
            waxOn = false;
            con.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void waitForWaxOn() throws InterruptedException {
        lock.lock();
        try {
            if (!waxOn)
                con.await();
        } finally {
            lock.unlock();
        }

    }
    public synchronized void waitForWaxOff() throws InterruptedException {
        lock.lock();
        try {
            if (waxOn)
                con.await();
        } finally {
            lock.unlock();
        }
    }
}

class WaxOn implements Runnable {
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.print("Wax On! ");
                TimeUnit.MILLISECONDS.sleep(200);
                car.waxOn();
                car.waitForWaxOff();
            }
        } catch (InterruptedException e) {
            System.out.println("Exiting via interrupt");
        }
        System.out.println("Ending Wax On task");

    }
}

class WaxOff implements Runnable {
    private Car car;

    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
            try {
                while (!Thread.interrupted()) {
                    System.out.print("Wax Off! ");
//                    TimeUnit.MILLISECONDS.sleep(200);
                    car.waxOff();
                    car.waitForWaxOn();
                }
            } catch (InterruptedException e) {
                System.out.println("Exiting via interrupt");
            }
            System.out.println("Ending Wax Off task");
    }
}