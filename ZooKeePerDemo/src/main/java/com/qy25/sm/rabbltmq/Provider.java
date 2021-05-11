package com.qy25.sm.rabbltmq;

import com.rabbitmq.client.Channel;


import com.rabbitmq.client.Connection;
import com.rabbitmq.client.MessageProperties;
import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;


import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
    /*    //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
//连接rabbitmq的主机
        connectionFactory.setHost("192.168.184.132");
//设置端口号
        connectionFactory.setPort(5672);
        //设置用户名和密码
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        //设置连接超时时间
        connectionFactory.setConnectionTimeout(300*1000);
        //握手超时设置需要有，因为不同机器之前的网络情况不同，因此可能导致网络超时而无法连接
        connectionFactory.setHandshakeTimeout(300*1000);
        //获取连接对象
        Connection connection = connectionFactory.newConnection();*/
        Connection connection = RabbitMQUtils.getConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //参数1: 是否持久化  参数2:是否独占队列 参数3:是否自动删除  参数4:其他属性
        channel.queueDeclare("hello", true, false, false, null);
        //参数一：交换机名称，参数二：队列名称，参数三：传递参数额外设置，参数4：消息的具体内容
        channel.basicPublish("", "hello", MessageProperties.TEXT_PLAIN, "hello rabbitmq".getBytes());
       RabbitMQUtils.closeConnectionAndChannel(channel,connection);
    }
}
