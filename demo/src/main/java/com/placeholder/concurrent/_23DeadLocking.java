package com.placeholder.concurrent;

import com.sun.org.apache.xpath.internal.SourceTree;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/8.
 */
public class _23DeadLocking {
    public static void main(String[] args) throws IOException, InterruptedException {
        int ponder = 5;
        int size = 5;
        Chopstick[] sticks = new Chopstick[size];
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i=0; i<size; i++)
            sticks[i] = new Chopstick();
        for (int i=0; i<size; i++) {
            executor.execute(new Philosopher(sticks[i], sticks[(i+1)%size], i, ponder));
        }
//        System.out.println("Press 'Enter' to quit");
        System.in.read();
        executor.shutdownNow();
    }
}

class Chopstick {
    private boolean taken = false;
    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();
        taken = true;
    }
    public synchronized void drop() throws InterruptedException {
        taken = false;
        notifyAll();
    }
}

class Philosopher implements Runnable {
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private final int ponderFactor;
    private Random rand = new Random(47);

    public Philosopher(Chopstick left, Chopstick right, int id, int ponderFactor) {
        this.left = left;
        this.right = right;
        this.id = id;
        this.ponderFactor = ponderFactor;
    }

    public void pause() throws InterruptedException {
        if(ponderFactor == 0) return;
        TimeUnit.MILLISECONDS.sleep(rand.nextInt(ponderFactor * 250));
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                System.out.print(this + " " + "thinking ");
                pause();
                System.out.print(this + " grabbling right ");
                right.take();
                System.out.print(this + " grabbling left ");
                left.take();
                System.out.print(this + " eating ");
                pause();
                right.drop();
                left.drop();
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
    }

    public String toString() {
        return "Philosopher " + id;
    }
}
