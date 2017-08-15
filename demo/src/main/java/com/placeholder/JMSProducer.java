package com.placeholder;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * Created by L on 2017/8/11.
 */
public class JMSProducer {
    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int SENDNUM = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;
        Connection connection = null;
        Destination destination;
        Session session;
        MessageProducer messageProducer;
        connectionFactory = new ActiveMQConnectionFactory(JMSProducer.USERNAME, JMSProducer.PASSWORD, JMSProducer.BROKERURL);

        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("Queue");
            messageProducer = session.createProducer(destination);
            sendMessage(session, messageProducer);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(connection != null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sendMessage(Session session,MessageProducer messageProducer) throws Exception{
        for (int i = 0; i < JMSProducer.SENDNUM; i++) {
            TextMessage message = session.createTextMessage("ActiveMQ 发送消息" +i);
            System.out.println("发送消息：Activemq 发送消息" + i);
            messageProducer.send(message);
        }
    }
}
