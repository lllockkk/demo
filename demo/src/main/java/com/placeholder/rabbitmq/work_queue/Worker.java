package com.placeholder.rabbitmq.work_queue;

import com.placeholder.rabbitmq.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This way of distributing messages is called round-robin
 */
public class Worker {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = Utils.getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(NewTask.QUEUE_NAME, true, false, false, null);

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                try {
                    doWork(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println(" [x] Done");
                }
            }
        };
        channel.basicConsume(NewTask.QUEUE_NAME, true, consumer);
    }

    public static void doWork(String task) throws InterruptedException {
        for (char c : task.toCharArray()) {
            if (c == '.')
                TimeUnit.SECONDS.sleep(1);
        }
    }
}
