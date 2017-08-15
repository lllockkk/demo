package com.placeholder.concurrent;

import org.apache.poi.poifs.crypt.dsig.OOXMLURIDereferencer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/8.
 */
public class _19Restaurant {
    Meal meal;
    WaitPerson waitPerson = new WaitPerson(this);
    Chef chef = new Chef(this);
    ExecutorService executor = Executors.newCachedThreadPool();

    public _19Restaurant() {
        executor.execute(waitPerson);
        executor.execute(chef);
    }

    public static void main(String[] args) {
        new _19Restaurant();
    }
}

class Meal {
    private final int orderNum;
    public Meal(int orderNum) {
        this.orderNum = orderNum;
    }
    public String toString() {
        return "Meal " + orderNum;
    }
}
class Chef implements Runnable {
    private _19Restaurant restaurant;
    private int count = 0;
    public Chef(_19Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal != null)
                        wait();
                }
                if (++count == 10) {
                    System.out.println("Out of food, closing");
                    restaurant.executor.shutdownNow();
                }
                System.out.println("Order up! ");
                synchronized (restaurant.waitPerson) {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.notifyAll();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Chef interrupted");
        }
    }
}

class WaitPerson implements Runnable {
    private _19Restaurant restaurant;

    public WaitPerson(_19Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    while (restaurant.meal == null)
                        wait();
                }
                System.out.println("get meal");
                synchronized (restaurant.chef) {
                    restaurant.meal = null;
                    restaurant.chef.notifyAll();
                }
            }
        } catch (InterruptedException e) {
            System.out.println("WaitPerson interrupted");
        }
    }
}
