package com.qy25.sm.rabbltmq;

import com.rabbitmq.client.*;
import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException {
       /* //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.184.132");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/ems");
        //设置连接超时时间
        connectionFactory.setConnectionTimeout(300*1000);
        //握手超时设置需要有，因为不同机器之前的网络情况不同，因此可能导致网络超时而无法连接
        connectionFactory.setHandshakeTimeout(300*1000);*/
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("hello", false, false, false, null);
        channel.basicConsume("hello",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body));
            }
        });
    }
}
