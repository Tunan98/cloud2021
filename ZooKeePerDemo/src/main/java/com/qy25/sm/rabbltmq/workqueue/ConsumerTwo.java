package com.qy25.sm.rabbltmq.workqueue;

import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumerTwo {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work",true,false,false,null);
        channel.basicConsume("work",true,new DefaultConsumer(channel){

            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                System.out.println("消费者-2: "+new String(body));
            }
        });
    }
}
