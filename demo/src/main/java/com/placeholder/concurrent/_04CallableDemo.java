package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/3.
 */
public class _04CallableDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<>();
        for (int i=0; i<10; i++) {
            futures.add(executor.submit(new TaskWithResult(i)));
        }
        try {
            for (Future<String> future : futures) {
                System.out.println(future.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}

class TaskWithResult implements Callable<String> {
    private int id;

    TaskWithResult(int id) {
        this.id = id;
    }

    @Override
    public String call() {
        return "result of " + id;
    }
}
