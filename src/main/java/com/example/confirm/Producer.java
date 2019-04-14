package com.example.confirm;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * confirm模式
 */
public class Producer {
    //定义队列名称
    private static final String QUEUE_NAME = "test_queue_confirm";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //生产者调用 confirmSelect() 将channel 设置为confirm模式
        channel.confirmSelect();

        //定义要发送的消息
        String msg = "Hello Confirm!";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());

        //确认是否发送成功
        if(channel.waitForConfirms()){
            System.out.println("成功发送了一条消息：" + msg);
        }else{
            System.out.println("发送消息失败！");
        }

        //关闭资源连接
        channel.close();
        connection.close();
    }
}