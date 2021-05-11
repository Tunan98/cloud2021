package com.qy25.sm.rabbitMQ;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Pruduct {
    @Test
    public void connect() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
//连接rabbitmq的主机
        connectionFactory.setHost("192.168.184.132");
//设置端口号
        connectionFactory.setPort(5672);
        //设置用户名和密码
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        connectionFactory.setVirtualHost("/ems");
//获取连接对象
        Connection connection = connectionFactory.newConnection();
        //创建通道
        Channel channel = connection.createChannel();
        //参数1: 是否持久化  参数2:是否独占队列 参数3:是否自动删除  参数4:其他属性
        channel.queueDeclare("hello", true, false, false, null);
        //参数一：交换机名称，参数二：队列名称，参数三：传递参数额外设置，参数4：消息的具体内容
        channel.basicPublish("", "hello", null, "hello rabbitmq".getBytes());
        channel.close();
        connection.close();
    }
}
