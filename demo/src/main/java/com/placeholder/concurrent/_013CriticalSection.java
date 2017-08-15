package com.placeholder.concurrent;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by L on 2017/8/6.
 */
public class _013CriticalSection {
    public static void main(String[] args) {
        PairManager
                pman1 = new PairManager1(),
                pman2 = new PairManager2();
        testApproaaches(pman1, pman2);
    }

    private static void testApproaaches(PairManager pman1, PairManager pman2) {
        PairManipulator
                pm1 = new PairManipulator(pman1),
                pm2 = new PairManipulator(pman2);
        PairChecker
                pc1 = new PairChecker(pman1),
                pc2 = new PairChecker(pman2);
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(pm1);
        executor.execute(pm2);
        executor.execute(pc1);
        executor.execute(pc2);
        executor.shutdown();
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            System.out.println("sleep interrupted");
        }
        System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
        System.exit(0);


    }
}

class Pair { // Not thread-safe
    private int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public Pair() {
        this(0, 0);
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public void incrementX() {
        x++;
    }
    public void incrementY() {
        y++;
    }
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
    public void checkState() {
        if (x != y)
            throw new PairValuesNotEqualException();
    }
    public class PairValuesNotEqualException extends RuntimeException {
        public PairValuesNotEqualException() {
            super("Pair values not equal: " + Pair.this);
        }
    }
}

abstract class PairManager {
    AtomicInteger checkCounter = new AtomicInteger(0);
    protected Pair p = new Pair();
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<>());

    public synchronized Pair getPair() {
        return new Pair(p.getX(), p.getY());
    }
    // Assume this is a time comsuming operation
    protected void store(Pair pair) {
        storage.add(pair);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            // ignore
        }
    }
    public abstract void increment();
}

class PairManager1 extends PairManager {
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        store(getPair());
    }
}

class PairManager2 extends PairManager {
    public void increment() {
        Pair temp;
        synchronized(this) {
            p.incrementX();
            p.incrementY();
            temp = getPair();
        }
        // 因为是耗时操作，所以把他放在synchronized语句块外面
        store(temp);
    }
}

class PairManipulator implements Runnable {
    private PairManager pm;

    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    public void run() {
        while (true)
            pm.increment();
    }

    public String toString() {
        return "Pair: " + pm.getPair() + " checkCounter= " + pm.checkCounter.get();
    }
}

class PairChecker implements Runnable {
    private PairManager pm;

    public PairChecker(PairManager pm) {
        this.pm = pm;
    }

    public void run() {
        while (true) {
            pm.checkCounter.incrementAndGet();
            pm.getPair().checkState();
        }
    }
}
