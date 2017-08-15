package com.placeholder.concurrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by L on 2017/8/8.
 */
public class _20TestBlockingQueue {
    public static void main(String[] args) {
        test("LinkedBlockingQueue", new LinkedBlockingQueue<_01LiftOff>());
        test("ArrayBlockingQueue", new ArrayBlockingQueue<_01LiftOff>(3));
        test("SynchronousQueue", new SynchronousQueue<>());
    }
    static void getkey() {
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            new RuntimeException(e);
        }
    }
    static void getkey(String message) {
        System.out.println(message);
        getkey();
    }
    static void test(String msg, BlockingQueue<_01LiftOff> rockets) {
        System.out.println(msg);
        LiftOffRunner runner = new LiftOffRunner(rockets);
        Thread t = new Thread(runner);
        t.start();
        for (int i=0; i<5; i++)
            runner.add(new _01LiftOff(5));
        getkey("Press 'Enter' (" + msg + ")");
        t.interrupt();
        System.out.println("Finished " + msg + " test");
    }
}

class LiftOffRunner implements Runnable {
    private BlockingQueue<_01LiftOff> rockets;

    public LiftOffRunner(BlockingQueue<_01LiftOff> rockets) {
        this.rockets = rockets;
    }

    public void add(_01LiftOff liftOff) {
        try {
            rockets.put(liftOff);
        } catch (InterruptedException e) {
            System.out.println("interrupted during put()");
        }
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                _01LiftOff liftOff = rockets.take();
                liftOff.run();
            }
        } catch (InterruptedException e) {
            System.out.println("Waking from take()");
        }
        System.out.println("Exiting LiftOffRunner");
    }
}
