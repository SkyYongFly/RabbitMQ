package com.example.topic;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 主题模式生产者：例如发布一个商品信息消息
 */
public class Producer {
    //定义交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();

        //声明交换机,设置为主题模式
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        //发送消息
        String msg = "Hello Topic !";

        //发布主题消息
        String type = "goods.add";
        channel.basicPublish(EXCHANGE_NAME, type, null, msg.getBytes());

        System.out.println("****发送了一条消息：" + msg + "  ；类型：" + type);

        //关闭资源连接
        channel.close();
        connection.close();
    }

}