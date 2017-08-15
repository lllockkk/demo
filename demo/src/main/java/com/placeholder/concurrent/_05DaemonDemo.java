package com.placeholder.concurrent;

import javax.sound.midi.Soundbank;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/3.
 */
public class _05DaemonDemo implements Runnable {
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(100);
            System.out.println(Thread.currentThread() + " " + this);
        } catch (InterruptedException e) {
            System.out.println("sleep() interrupted");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i=0; i<10; i++) {
            Thread t = new Thread(new _05DaemonDemo());
            t.setDaemon(true);
            t.start();
        }

        System.out.println("all daemons start");
        TimeUnit.MILLISECONDS.sleep(100);
    }
}
