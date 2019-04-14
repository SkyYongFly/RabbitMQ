package com.example.ps;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 订阅模式生产者：只负责把消息发送到交换机
 */
public class Producer {
    //定义交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout"); //分发
        //发送消息
        String msg = "Hello Publish_Subscribe !";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("****发送了一条消息：" + msg);

        //关闭资源连接
        channel.close();
        connection.close();
    }

}