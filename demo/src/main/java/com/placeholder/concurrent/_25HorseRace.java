package com.placeholder.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by L on 2017/8/9.
 */
public class _25HorseRace {
    static final int FINISH_LINE = 75;
    private List<Horse> horses = new ArrayList<Horse>();
    private ExecutorService executor = Executors.newCachedThreadPool();
    private CyclicBarrier barrier;

    public _25HorseRace(int nHorses, int pause) {
        barrier = new CyclicBarrier(nHorses, () -> {
           StringBuilder sb = new StringBuilder();
           for (int i=0; i<FINISH_LINE; i++)
               sb.append("=");
            System.out.println(sb);
            for (Horse horse : horses)
                System.out.println(horse.tracks());
            for (Horse horse : horses) {
                if (horse.getStride() >= FINISH_LINE) {
                    System.out.println(horse + "won!");
                    executor.shutdownNow();
                    return;
                }
            }
            try {
                TimeUnit.MILLISECONDS.sleep(pause);
            } catch (InterruptedException e) {
                System.out.println("barrier-action sleep interrupted");
            }
        });
        for (int i=0; i<nHorses; i++) {
            Horse horse = new Horse(barrier);
            horses.add(horse);
            executor.execute(horse);
        }

    }

    public static void main(String[] args) {
        new _25HorseRace(5, 200);
    }
}

class Horse implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private static CyclicBarrier barrier;
    private static Random rand = new Random(47);
    private int stride;
    public Horse(CyclicBarrier barrier) {
        this.barrier = barrier;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                synchronized (this) {
                    stride += rand.nextInt(3);
                }
                barrier.await();
            }
        } catch (InterruptedException e) {

        } catch (BrokenBarrierException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized int getStride() {
        return stride;
    }

    @Override
    public String toString() {
        return "Horse " + id + " ";
    }

    public String tracks() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < stride; i++)
            sb.append("*");
        sb.append(id);
        return sb.toString();
    }
}
