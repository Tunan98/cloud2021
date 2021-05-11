package com.qy25.sm.rabbltmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;
    static {
        connectionFactory=new ConnectionFactory();
        connectionFactory.setHost("192.168.184.132");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");
        connectionFactory.setConnectionTimeout(300*1000);
        connectionFactory.setHandshakeTimeout(300*1000);
    }
    //连接对象的提供方法
    public static Connection getConnection(){
      try {
          return connectionFactory.newConnection();
      }catch (Exception e){
          e.printStackTrace();
      }
      return null;
    }
    //关闭通道和关闭连接工具方法
    public static void closeConnectionAndChannel(Channel channel, Connection connection){
        try {
            if (channel!=null){
                channel.close();
            }
            if (connection!=null){
                connection.close();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
