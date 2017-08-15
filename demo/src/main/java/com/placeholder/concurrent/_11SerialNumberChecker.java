package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/6.
 */
public class _11SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private static ExecutorService executor = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable {
        public void run() {
            while (true) {
                int value = SerialNumberGenerator.nextSerialNumber();
                if (serials.contain(value)) {
                    System.out.println("Dulplicate " + value);
                    System.exit(0);
                }
                serials.add(value);
            }
        }
    }

    public static void main(String[] args) {
        for (int i=0; i<SIZE; i++)
            executor.execute(new SerialChecker());
        executor.shutdown();
    }
}

class SerialNumberGenerator {
    private static int serialNumber = 0;
    public static synchronized int nextSerialNumber() {
        return serialNumber ++; // not thread-safe
    }
}

class CircularSet {
    private int[] array;
    private int index;
    private int len;

    public CircularSet(int size) {
        array = new int[size];
        for (int i=0; i<size; i++) {
            array[i] = -1;
        }

        index = 0;
        this.len = size;
    }

    public synchronized void add(int item) {
        array[index] = item;
        index = ++index % len;
    }

    public synchronized boolean contain(int item) {
        for (int i : array) {
            if (item == i)
                return true;
        }
        return false;
    }
}
