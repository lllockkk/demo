package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/9.
 */
public class _26DelayQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        Random rand = new Random(47);
        ExecutorService executor = Executors.newCachedThreadPool();
        DelayQueue<DelayedTask> queue = new DelayQueue<>();
        for (int i=0; i<20; i++)
            queue.put(new DelayedTask(rand.nextInt(5000)));
        queue.add(new DelayedTask.EndSentinel(5000, executor));
        executor.execute(new DelayedTaskConsumer(queue));
    }
}

class DelayedTask implements Runnable, Delayed {
    private static int counter = 0;
    private final int id = counter++;
    private final int delta;
    private final long trigger;
    protected static List<DelayedTask> sequence = new ArrayList<>();
    public DelayedTask(int delayInMilliseconds) {
        delta = delayInMilliseconds;
        trigger = System.nanoTime() + TimeUnit.NANOSECONDS.convert(delta, TimeUnit.MILLISECONDS);
        sequence.add(this);
    }
    public long getDelay(TimeUnit unit) {
        return unit.convert(trigger - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        DelayedTask that = (DelayedTask) o;
        if(trigger < that.trigger) return -1;
        if(trigger > that.trigger) return 1;
        return 0;
    }

    public void run() {
        System.out.println(this + " ");
    }
    public String summary() {
        return "(" + id + ":" + delta + ")";
    }
    public String toString() {
        return String.format("[%1$-4d]", delta) + " Task " + id;
    }

    public static class EndSentinel extends DelayedTask {
        private ExecutorService executor;
        public EndSentinel(int delayInMilliseconds, ExecutorService executor) {
            super(delayInMilliseconds);
            this.executor = executor;
        }

        @Override
        public void run() {
            for (DelayedTask task : sequence) {
                System.out.println(task.summary() + " ");
            }
            System.out.println(this + " Calling shutdownNow()");
            executor.shutdownNow();
        }
    }
}

class DelayedTaskConsumer implements Runnable {
    private DelayQueue<DelayedTask> q;

    public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
        this.q = q;
    }

    public void run() {
        try {
            while (!Thread.interrupted())
                q.take().run();
        } catch (InterruptedException e) {

        }
        System.out.println("Finished DelayedTaskConsumer");
    }
}
