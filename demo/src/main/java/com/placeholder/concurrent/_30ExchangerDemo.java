package com.placeholder.concurrent;

import java.util.List;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/9.
 */
public class _30ExchangerDemo {
    static int size = 10;
    static int delay = 5;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        Exchanger<List<Fat>> ec = new Exchanger<>();
        List<Fat>
                producerList = new CopyOnWriteArrayList<>(),
                consumerList = new CopyOnWriteArrayList<>();
        executor.execute(new ExchangerProducer<>(ec, BasicGenerator.create(Fat.class), producerList));
        executor.execute(new ExchangerConsumer<>(ec, consumerList));
        TimeUnit.SECONDS.sleep(delay);
        executor.shutdownNow();
    }
}

class ExchangerProducer<T> implements Runnable {
    private Generator<T> generator;
    private Exchanger<List<T>> exchanger;
    private List<T> holder;

    public ExchangerProducer(Exchanger<List<T>> exchanger, Generator<T> gen, List<T> holder) {
        this.exchanger = exchanger;
        this.generator = gen;
        this.holder = holder;
    }

    public void run() {
        try {
            while (!Thread.interrupted()) {
                for (int i=0; i<_30ExchangerDemo.size; i++) {
                    holder.add(generator.next());
                }
                // Exchange full for empty
                holder = exchanger.exchange(holder);
            }
        } catch(InterruptedException e) {
            System.out.println("ExchangeProducer interrupted");
        }
    }
}

class ExchangerConsumer<T> implements Runnable {
    private Exchanger<List<T>> exchanger;
    private List<T> holder;
    private volatile T value;
    ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder) {
        this.exchanger = ex;
        this.holder = holder;
    }
    public void run() {
        try {
            while (!Thread.interrupted()) {
                holder = exchanger.exchange(holder);
                for (T t : holder) {
                    value = t;
                    holder.remove(t);
                }
            }
        } catch (InterruptedException e) {
            System.out.println("ExchangerConsumer interrupted");
        }
        System.out.println("Final value: " + value);
    }
}

interface Generator<T> {
    T next();
}

/*class BasicGenerator implements Generator<T> {
    private Class<T> clazz;
    public T next() {
        T t = null;
        try {
            t = T.class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
    private BasicGenerator(Class<T> c) {
        this.clazz = c;
    }
    public static Generator<T> create(Class<T> c) {
        return new BasicGenerator(c);
    }
}*/

class BasicGenerator<T> implements Generator<T> {
    private Class<T> clazz;
    public T next() {
        T t = null;
        try {
            t = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
    private BasicGenerator(Class<T> c) {
        this.clazz = c;
    }

    public static <V> BasicGenerator<V> create(Class<V> c) {
        return new BasicGenerator<>(c);
    }
}


