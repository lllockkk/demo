package com.placeholder.rabbitmq.work_queue;

import com.placeholder.rabbitmq.Utils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class NewTask {
    public static final String QUEUE_NAME = "task_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = Utils.getConnectionFactory();
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            String message = getMessage(args);
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes("UTF-8"));
        }
    }

    private static String getMessage(String[] args) {
        if (args.length < 1) {
            return "Hello World!";
        }

        return joinString(args, " ");
    }

    private static String joinString(String[] args, String delimiter) {
        int len = args.length;
        if (len == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(args[0]);
        for (int i=1; i<len; i++) {
            sb.append(delimiter).append(args[i]);
        }
        return sb.toString();
    }
}
