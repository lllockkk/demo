package com.placeholder.concurrent;

import com.sun.org.apache.xpath.internal.SourceTree;
import sun.util.resources.cldr.so.CurrencyNames_so;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/9.
 */
public class _31BankTellerSimulation {
    private static final int MAX_LINE_SIZE = 50;
    private static final int ADJUSTMENT_PERIOD = 1000;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        CustomerLine customers = new CustomerLine(MAX_LINE_SIZE);
        executor.execute(new CustomerGenerator(customers));
        executor.execute(new TellerManager(executor, customers, ADJUSTMENT_PERIOD));
        Thread.sleep(5000);
        executor.shutdownNow();
    }
}

class Customer {
    private final int serveTime;
    public Customer(int serveTime) {
        this.serveTime = serveTime;
    }
    public int getServeTime() {
        return serveTime;
    }
    public String toString() {
        return "[" + serveTime + "]";
    }
}

class CustomerLine extends ArrayBlockingQueue<Customer> {
    public CustomerLine(int maxCustomerLine) {
        super(maxCustomerLine);
    }

    @Override
    public String toString() {
        if (this.isEmpty())
            return "[Empty]";

        StringBuilder sb = new StringBuilder();
        for (Customer customer : this)
            sb.append(customer);
        return sb.toString();
    }
}

class CustomerGenerator implements Runnable {
    private CustomerLine customers;
    private static final Random rand = new Random(47);

    public CustomerGenerator(CustomerLine customers) {
        this.customers = customers;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(300);
                customers.put(new Customer(rand.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator interrupted");
        }
        System.out.println("CustomerGenerator finished");
    }
}

class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private int id = counter++;
    private boolean isServe = true;
    private int serveCount = 0;
    private CustomerLine customers;

    public Teller(CustomerLine customers) {
        this.customers = customers;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer customer = customers.take();
                TimeUnit.MILLISECONDS.sleep(customer.getServeTime());

                synchronized (this) {
                    serveCount++;
                    while (isServe)
                        wait();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + " interrupted");
        }
        System.out.println(this + "finish");
    }
    public synchronized void doSomethingElse() {
        serveCount = 0;
        isServe = false;
    }
    public synchronized void serveCustomer() {
        assert !isServe : "already serving: " + this;
        isServe = true;
        notifyAll();
    }
    public String toString() {
        return "Teller " + id + " ";
    }
    public String shortString() {
        return "T" + id;
    }

    public synchronized int compareTo(Teller o) {
        return serveCount - o.serveCount;
    }
}
class TellerManager implements Runnable {
    private ExecutorService executor;
    private PriorityBlockingQueue<Teller> workingTellers = new PriorityBlockingQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<>();
    private CustomerLine customers;
    private static Random rand = new Random(47);
    private int period;

    public TellerManager(ExecutorService executor, CustomerLine customers, int period) {
//        this.tellers = tellers;
        this.executor = executor;
        this.period = period;
        this.customers = customers;
        Teller teller = new Teller(customers);
        executor.execute(teller);
        workingTellers.add(teller);
    }

    public void adjuestNumber() throws InterruptedException {
        if (customers.size() / workingTellers.size() > 2) {
            if (tellersDoingOtherThings.isEmpty()) {
                Teller teller = new Teller(customers);
                executor.execute(teller);
                workingTellers.add(teller);
            } else {
                Teller teller = tellersDoingOtherThings.remove();
                teller.serveCustomer();
                workingTellers.add(teller);
            }
        }
        if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }

        if (customers.isEmpty()) {
            while (workingTellers.size() > 1)
                reassignOneTeller();
        }
    }

    public void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        tellersDoingOtherThings.offer(teller);
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(period);
                adjuestNumber();
                System.out.print(customers + " { ");
                for (Teller teller : workingTellers)
                    System.out.print(teller.shortString() + " ");
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println("TellerManager interrupted");
        }
        System.out.println(this + "finish");
    }

    public String toString() {
        return "TellerManager ";
    }
}