package com.placeholder.rabbitmq.topic;

import com.placeholder.rabbitmq.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = Utils.getConnectionFactory();
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(Publisher.EXCHANGE_NAME, "topic");
        String queue = channel.queueDeclare().getQueue();
        channel.queueBind(queue, Publisher.EXCHANGE_NAME, "com.#");
        channel.queueBind(queue, Publisher.EXCHANGE_NAME, "#.rabbitmq");
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("[Recv] " + message);
            }
        });
    }
}
