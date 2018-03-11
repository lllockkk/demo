package com.placeholder.xml.consumer;

import com.placeholder.xml.service.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by L on 2018/3/4.
 */
public class ConsumerMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("consumer.xml");
        context.start();
        DemoService demoService = (DemoService) context.getBean("demoService");
        String result = demoService.sayHello("world");
        System.out.println(result);
    }
}
