package com.example.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ连接工具类
 */
public class ConnectionUtils {
    /**
     * 获取RabbitMQ连接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置服务地址
        connectionFactory.setHost("127.0.0.1");
        //设置AMQP监听端口
        connectionFactory.setPort(5672);
        //设置vhost
        connectionFactory.setVirtualHost("/example");
        //用户名
        connectionFactory.setUsername("admin");
        //密码
        connectionFactory.setPassword("admin");
        
        return connectionFactory.newConnection();
    }
}