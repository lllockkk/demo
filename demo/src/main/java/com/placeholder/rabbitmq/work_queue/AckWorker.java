package com.placeholder.rabbitmq.work_queue;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * This way of distributing messages is called round-robin
 */
public class AckWorker {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(NewTask.QUEUE_NAME, true, false, false, null);

        // accept only one unack-ed message at a time (see below)
        channel.basicQos(1);

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

                    // 手动应答
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };
        // 手动应答
        boolean autoAck = false;
        channel.basicConsume(NewTask.QUEUE_NAME, autoAck, consumer);
    }

    public static void doWork(String task) throws InterruptedException {
        for (char c : task.toCharArray()) {
            if (c == '.')
                TimeUnit.SECONDS.sleep(1);
        }
    }
}
