package com.placeholder;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by L on 2017/8/14.
 */
public class MQTest {
    static String DEFAULTUSER = ActiveMQConnection.DEFAULT_USER;
    static String DEFAULTPASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    static String DEFAULTBROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        try {
            ExecutorService executor = Executors.newCachedThreadPool();
            executor.execute(new com.placeholder.MQProducer());
            executor.execute(new com.placeholder.MQConsumer());

//            TimeUnit.SECONDS.sleep(10);
//            executor.shutdownNow();
        } catch (Exception e) {
            System.out.println("app exception");
        }
    }
}

class MQProducer implements Runnable {
        private int counter = 0;

        public void run() {
            try {
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQTest.DEFAULTUSER, MQTest.DEFAULTPASSWORD, MQTest.DEFAULTBROKERURL);
                Connection connection = factory.createConnection();
//            connection.start();
                Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
                MessageProducer producer = session.createProducer(session.createQueue("testQueue"));
                while (!Thread.interrupted()) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("before");
                    producer.send(session.createTextMessage("test message " + counter++));
                    System.out.println("after");
                    session.commit();
                }
            } catch (Exception e) {
                System.out.println("producer exception");
            }
        }
    }

class MQConsumer implements Runnable {
        public void run() {
            try {
                ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(MQTest.DEFAULTUSER, MQTest.DEFAULTPASSWORD, MQTest.DEFAULTBROKERURL);
                Connection connection = factory.createConnection();
                connection.start();
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                MessageConsumer consumer = session.createConsumer(session.createQueue("testQueue"));
                consumer.setMessageListener(new MessageListener() {
                    @Override
                    public void onMessage(Message message) {
                        TextMessage textMessage = (TextMessage) message;
                        try {
                            System.out.println(textMessage.getText());
                        } catch (JMSException e) {
                            System.out.println("listener exception");
                        }
                    }
                });
            /*while (!Thread.interrupted()) {
                Thread.sleep(1000);
                System.out.println("test");
                *//*Message receive = consumer.receive();
                System.out.println(((TextMessage)receive).getText());*//*
            }*/
            } catch (Exception e) {
                System.out.println("consumer exception");
            }
        }
    }
