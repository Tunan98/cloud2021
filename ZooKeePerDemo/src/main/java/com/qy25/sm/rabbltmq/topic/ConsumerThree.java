package com.qy25.sm.rabbltmq.topic;

import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;
import com.rabbitmq.client.*;

import java.io.IOException;

public class ConsumerThree {
    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();
        String directName ="topics";
        //通道声明交换机以及交换的类型
        channel.exchangeDeclare(directName,"topic");
        //创建一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //基于rote_key绑定队列和交换机
        String route_key="*.user.*";//三个单词中间的单词必须是user
        channel.queueBind(queueName,directName,route_key);
        //获取消费的消息
        channel.basicConsume(queueName,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1:"+new String(body));
            }
        });
    }
}
