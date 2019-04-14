package com.example.workfair;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * 工作队列之公平分发生产者
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

        //设置每次发送到队列的消息只有一个，需要等到消费者发送处理完的响应后才继续发送消息
        int prefetchCount = 1;
        channel.basicQos(prefetchCount);

        for(int i = 0; i < 50; i++){
            //定义要发送的消息
            String msg = "Message [" + i + "]";
            //发送消息
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("----发送了一条消息：" + msg);

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //关闭资源连接
        channel.close();
        connection.close();
    }

}