package com.qy25.sm.rabbltmq.direct;

import com.qy25.sm.rabbltmq.utils.RabbitMQUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;

public class Provider {
    public static void main(String[] args) throws IOException {
        String directName ="logs_direct";
        //获取连接
        Connection connection = RabbitMQUtils.getConnection();
        //获取连接通道对象
        Channel channel = connection.createChannel();
        //通过通道声明交换机，参数1:交换机名称 参数二:direct 路由模式
        channel.exchangeDeclare(directName,"direct");
        //发送消息，设置路由
        String routingKey ="waring";
        channel.basicPublish(directName,routingKey,null,("这是direct模型发布的基于route key:{"+routingKey+"} 发送的消息").getBytes());
        //关闭资源
        RabbitMQUtils.closeConnectionAndChannel(channel,connection);

    }
}
