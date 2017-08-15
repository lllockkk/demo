package com.placeholder;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class App {
    public static void main(String[] args) throws Exception {

    }
}

class Test implements A, B {
    @Override
    public void test() {

    }

    @Override
    public void a() {

    }

    @Override
    public void b() {

    }
}

interface A {
    void test();
    void a();
}

interface B {
    int test();
    void b();
}