package com.placeholder.concurrent;

/**
 * Created by L on 2017/8/3.
 */
public class _01LiftOff implements Runnable {
    private int countDown = 10;
    private static int taskCount = 0;
    private final int id = taskCount++;

    public _01LiftOff() {
    }
    public _01LiftOff(int countDown) {
        this.countDown = countDown;
    }

    public String status() {
        return "#" + id + "(" + (countDown>0 ? countDown:"liftoff!") + ")";
    }

    @Override
    public void run() {
        while (countDown-- > 0) {
            System.out.println(status());
            Thread.yield();
        }
    }

    public static void main(String[] args) {
        for (int i=0; i<5; i++)
            new Thread(new _01LiftOff()).start();

        System.out.println("start");
    }
}
