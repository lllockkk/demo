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
        boolean flag = C.class.isAnnotationPresent(A.class);
        C.class.getAnnotations();
        System.out.println(flag);
    }
}

@B
class C {

}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@interface A {

}

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@A
@interface B {

}


