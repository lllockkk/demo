package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/9.
 */
public class _29SemaphoreDemo {
    final static int SIZE = 25;
    public static void main(String[] args) throws InterruptedException {
        final Pool<Fat> pool = new Pool<>(Fat.class, SIZE);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0; i<SIZE; i++) {
            executor.execute(new CheckoutTask<>(pool));
        }
        System.out.println("All checkoutTasks created");
        List<Fat> list = new ArrayList<>();
        for (int i=0; i<SIZE; i++) {
            Fat f = pool.checkOut();
            System.out.print(i + ": main() thread checked out ");
            f.operation();
            list.add(f);
        }
        Future<?> blocked = executor.submit(() -> {
            try {
                pool.checkOut();
            } catch (InterruptedException e) {
                System.out.println("checkOut() interrupted");
            }
        });
        TimeUnit.SECONDS.sleep(2);
        blocked.cancel(true);
        System.out.println("Checking in objects in " + list);
        for (Fat f : list)
            pool.checkIn(f);
        for (Fat f : list)
            pool.checkIn(f);    // Second checkIn ignored
        executor.shutdown();
    }
}
class Pool<T> {
    private int size;
    private List<T> items = new ArrayList<>();
    private volatile boolean[] checkedOut;
    private Semaphore available;

    public Pool(Class<T> classObject, int size) {
        this.size = size;
        checkedOut = new boolean[size];
        available = new Semaphore(size, true);

        for (int i=0; i<size; i++) {
            try {
                items.add(classObject.newInstance());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public T checkOut() throws InterruptedException {
        available.acquire();
        return getItem();
    }
    public void checkIn(T t) {
        if(releaseItem(t))
            available.release();
    }
    private synchronized T getItem() {
        for (int i=0; i<size; i++) {
            if (!checkedOut[i]) {
                checkedOut[i] = true;
                return items.get(i);
            }
        }
        return null;
    }
    private synchronized boolean releaseItem(T t) {
        int index = items.indexOf(t);
        if(index == -1)
            return false;
        if(checkedOut[index]) {
            checkedOut[index] = false;
            return true;
        }
        return false;
    }
}
class Fat {
    private volatile double d; // prevent optimization
    private static int counter = 0;
    private final int id = counter++;

    public Fat() {
        for (int i=1; i<10000; i++) {
            d += (Math.PI + Math.E) / (double)i;
        }
    }
    public void operation() {
        System.out.println(this);
    }
    public String toString() {
        return "Fat id: " + id;
    }
}

class CheckoutTask<T> implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private Pool<T> pool;

    public CheckoutTask(Pool<T> pool) {
        this.pool = pool;
    }
    public void run() {
        try {
            T t = pool.checkOut();
            System.out.println(this + " checked out " + t);
            TimeUnit.SECONDS.sleep(1);
            System.out.println(this + " checking in " + t);
            pool.checkIn(t);
        } catch (InterruptedException e) {
            System.out.println("CheckoutTask interrupted");
        }
    }
    public String toString() {
        return "CheckoutTask " + id;
    }
}
