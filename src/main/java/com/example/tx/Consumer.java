package com.example.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 *  简单队列消费者
 */
public class Consumer {
    //获取消息的队列名称
    private static final String QUEUE_NAME = "test_queue_transaction";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();

        //创建频道
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body);
                System.out.println("****收到了一条消息：" + msg);
            }
        };

        //监听队列
        channel.basicConsume(QUEUE_NAME, consumer);
    }

}