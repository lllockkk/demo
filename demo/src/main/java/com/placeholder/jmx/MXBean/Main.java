package com.placeholder.jmx.MXBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws Exception {
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("com.placeholder.jmx.MXBean:type=QueueSampler");
        Queue<String> queue = new ArrayBlockingQueue<>(10);
        queue.add("first");
        queue.add("second");
        queue.add("third");
        QueueSampler sampler = new QueueSampler(queue);
        mbs.registerMBean(sampler, name);

        System.out.println("Waiting...");
        TimeUnit.SECONDS.sleep(Long.MAX_VALUE);
    }
}
