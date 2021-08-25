package com.placeholder;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.state.SessionState;
import org.apache.activemq.usage.StoreUsage;
import org.springframework.jms.core.JmsTemplate;
import redis.clients.jedis.*;

import javax.jms.*;
import javax.jms.Connection;
import java.lang.annotation.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Demo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        A a = new A();
        executor.submit(a::a);

        executor.submit(a::a);
    }
}

class A {
    public synchronized void a() {
        while (true) {
            try {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(Thread.currentThread() + " hello");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


