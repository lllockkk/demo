package com.placeholder.concurrent;

/**
 * Created by L on 2017/8/5.
 */
public class _07Joiner {
    public static void main(String[] args) throws InterruptedException {
        Sleeper s1 = new Sleeper("s1", 1500);
        Sleeper s2 = new Sleeper("s2", 1500);
        Joining j1 = new Joining("j1", s1);
        Joining j2 = new Joining("j2", s2);

        s2.interrupt();
    }
}

class Sleeper extends Thread {
    private long duration;

    public Sleeper(String name, long duration) {
        super(name);
        this.duration = duration;
        start();
    }

    public void run() {
        try {
            System.out.println(getName() + " run");
            sleep(duration);
            System.out.println(getName() + " has awakened up");
        } catch (InterruptedException e) {
            System.out.println(getName() + " sleep interrupt");
        }
    }
}

class Joining extends Thread {
    private Sleeper sleeper;
    public Joining(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
            System.out.println(getName() + " join completed");
        } catch (InterruptedException e) {
            System.out.println("sleep interrupt");
        }
    }
}
