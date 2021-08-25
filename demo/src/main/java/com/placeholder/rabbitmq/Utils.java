package com.placeholder.rabbitmq;

import com.rabbitmq.client.ConnectionFactory;

public class Utils {
    public static ConnectionFactory getConnectionFactory() {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.232.131");
        return factory;
    }
}
