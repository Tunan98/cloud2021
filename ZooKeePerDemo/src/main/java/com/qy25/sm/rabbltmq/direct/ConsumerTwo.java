package com.qy25.sm.rabbltmq.direct;

import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumerTwo {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String directName ="logs_direct";
        //通道声明交换机以及交换的类型
        channel.exchangeDeclare(directName,"direct");
        //创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //基于rote_key绑定队列和交换机
        channel.queueBind(queueName,directName,"error");
        channel.queueBind(queueName,directName,"info");
        channel.queueBind(queueName,directName,"waring");
        //获取消费的消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2:"+new String(body));
            }
        });

    }
}
