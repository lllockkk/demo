package com.placeholder.rabbitmq.topic;

import com.placeholder.rabbitmq.Utils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Publisher {
    public static final String EXCHANGE_NAME = "mytopic";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = Utils.getConnectionFactory();
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare("mytopic", "topic");

            channel.basicPublish("mytopic", "com.a", null, "com".getBytes("UTF-8"));
            channel.basicPublish("mytopic", "com.placeholder", null, "com.placeholder".getBytes("UTF-8"));
            channel.basicPublish("mytopic", "com.placeholder.rabbitmq", null, "com.placeholder.rabbitmq".getBytes("UTF-8"));
        }
    }
}
