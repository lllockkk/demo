package com.placeholder.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/8.
 */
public class _21ToastOMatic {
    public static void main(String[] args) throws InterruptedException {
        ToastQueue
                toastQueue = new ToastQueue(),
                butteredQueue = new ToastQueue(),
                jammedQueue = new ToastQueue();
        ExecutorService executor = Executors.newCachedThreadPool();
        executor.execute(new Toaster(toastQueue));
        executor.execute(new Butterer(toastQueue, butteredQueue));
        executor.execute(new Jammer(butteredQueue, jammedQueue));
        executor.execute(new Eater(jammedQueue));

        TimeUnit.SECONDS.sleep(5);
        executor.shutdownNow();
    }
}

class Toast {
    public enum Status { DRY, BUTTERED, JAMMED }
    private Status status = Status.DRY;
    private final int id;
    public Toast(int id) {
        this.id = id;
    }

    public void butter() {
        status = Status.BUTTERED;
    }
    public void jam() {
        status = Status.JAMMED;
    }
    public Status getStatus() {
        return status;
    }
    public int getId() {
        return id;
    }
    public String toString() {
        return "Toast " + id + ": " + status + " ";
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {

}

class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count =0;
    private Random rand = new Random(47);

    public Toaster(ToastQueue toastQueue) {
        this.toastQueue = toastQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(100 + rand.nextInt(500));
                Toast toast = new Toast(count++);
                System.out.print(toast);
                toastQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted during toast");
        }
        System.out.println("Toaster off");
    }
}

class Butterer implements Runnable {
    private ToastQueue toastQueue, butteredQueue;

    public Butterer(ToastQueue toastQueue, ToastQueue butteredQueue) {
        this.toastQueue = toastQueue;
        this.butteredQueue = butteredQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = toastQueue.take();
                toast.butter();
                System.out.print(toast);
                butteredQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted during butter");
        }
        System.out.println("Butter off");
    }
}

class Jammer implements Runnable {
    private ToastQueue butteredQueue, jammedQueue;

    public Jammer(ToastQueue butteredQueue, ToastQueue jammedQueue) {
        this.butteredQueue = butteredQueue;
        this.jammedQueue = jammedQueue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = butteredQueue.take();
                toast.jam();
                System.out.print(toast);
                jammedQueue.put(toast);
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted during jam");
        }
    }
}

class Eater implements Runnable {
    private ToastQueue jammedQueue;
    private int counter = 0;

    public Eater(ToastQueue jammedQueue) {
        this.jammedQueue = jammedQueue;
    }

     public void run() {
        try {
            while (!Thread.interrupted()) {
                Toast toast = jammedQueue.take();
                if (counter++ != toast.getId() || toast.getStatus() != Toast.Status.JAMMED) {
                    System.out.println(">>>> Error: " + toast);
                    System.exit(1);
                } else {
                    System.out.println("Chomp! " + toast);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("interrupted during eat");
        }
         System.out.println("Eat Off");
    }
}