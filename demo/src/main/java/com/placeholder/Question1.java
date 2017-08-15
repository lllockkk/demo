package com.placeholder;

import javax.naming.SizeLimitExceededException;

/**
 * Created by L on 2017/8/8.
 */
public class Question1 {
    public static void main( String[] args ) throws InterruptedException {
        Flag flag = new Flag();
        new Thread(new Task(flag)).start();
        Thread.sleep(100);
        flag.setRun(false);
    }
}
class Flag {
    private boolean run = true;
    public boolean isRun() {
//        System.out.println("test"); // todo 注释掉该语句就不会停止运行Task
        return run;
    }
    public void setRun(boolean run) {
        this.run = run;
    }
}

class Task implements Runnable {
    private final Flag flag;
    public Task(Flag flag) {
        this.flag = flag;
    }
    public void run() {
        System.out.println("begin");
        while (flag.isRun()) {
//            System.out.println("hello"); // todo 注释掉该语句就不会停止运行Task
        }
        System.out.println("end");
    }
}
