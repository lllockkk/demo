package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/9.
 */
public class _27PriorityBlockingQueueDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        PriorityBlockingQueue queue = new PriorityBlockingQueue();
        executor.execute(new PrioritizedTaskProducer(queue, executor));
        executor.execute(new PrioritizedTaskConsumer(queue));
    }
}

class PrioritizedTask implements Runnable, Comparable<PrioritizedTask> {
    private static int counter = 0;
    private final int id = counter++;
    private final int priority;
    private static Random rand = new Random(47);
    protected static List<PrioritizedTask> sequence = new ArrayList<>();

    public PrioritizedTask(int priority) {
        this.priority = priority;
        sequence.add(this);
    }

    public int compareTo(PrioritizedTask o) {
        return o.priority - priority;
    }

    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(rand.nextInt(250));
        } catch (InterruptedException e) {
            System.out.println("task interrupted");
        }
        System.out.println(this);
    }

    public String summary() {
        return "(" + id + ":" + priority + ")";
    }
    public String toString() {
        return String.format("[%1$-3d]", priority) + " Task " + id;
    }

    public static class EndSentinel extends PrioritizedTask {
        private ExecutorService executor = Executors.newCachedThreadPool();
        public EndSentinel(ExecutorService executor) {
            super(-1);
            this.executor = executor;
        }
        public void run() {
            for (PrioritizedTask task : sequence) {
                System.out.println(task.summary());
            }
            System.out.println(this + " Calling shutdownNow()");
            executor.shutdownNow();
        }
    }
}

class PrioritizedTaskProducer implements Runnable {
    private Random rand = new Random(47);
    private Queue<Runnable> queue;
    private ExecutorService executor;
    public PrioritizedTaskProducer(Queue<Runnable> queue, ExecutorService executor) {
        this.queue = queue;
        this.executor = executor;
    }
    public void run() {
        for (int i=0; i<20; i++) {
            queue.add(new PrioritizedTask(rand.nextInt(10)));
            Thread.yield();
        }
        try {
            for (int i=0; i<10; i++) {
                TimeUnit.MILLISECONDS.sleep(250);
                queue.add(new PrioritizedTask(10));
            }
            for (int i=0; i<10; i++)
                queue.add(new PrioritizedTask(i));
            queue.add(new PrioritizedTask.EndSentinel(executor));
        } catch (InterruptedException e) {
            System.out.println("producer interrupted");
        }
        System.out.println("Finished PrioritizedTaskProducer");
    }
}

class PrioritizedTaskConsumer implements Runnable {
    private PriorityBlockingQueue<Runnable> queue;
    public PrioritizedTaskConsumer(PriorityBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                queue.take().run();
            }
        } catch (InterruptedException e) {
            System.out.println("Consumer interrupted");
        }
        System.out.println("Finished PrioritizedTaskConsumer");
    }
}
