package com.qy25.sm.rabbltmq.workqueue;

import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("work",true,false,false,null);
        for (int i = 0; i < 20; i++) {
            //生产消息
            channel.basicPublish("","work",null,(i+"hello work queue").getBytes());
        }
            RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
