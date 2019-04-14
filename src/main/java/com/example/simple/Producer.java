package com.example.simple;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 生产者：发布消息
 */
public class Producer {
    //定义队列名称
    private static final String QUEUE_NAME = "test_queue_name";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //定义要发送的消息
        String msg = "Hello RabbitMQ!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("----发送了一条消息：" + msg);

        //关闭资源连接
        channel.close();
        connection.close();
    }
}