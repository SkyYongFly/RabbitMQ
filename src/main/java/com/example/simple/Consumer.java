package com.example.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者：获取生产者发送的消息
 */
public class Consumer {
    //获取消息的队列名称
    private static final String QUEUE_NAME = "test_queue_name";

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

    private static void oldMethod() throws IOException, TimeoutException, InterruptedException {
        //获取连接
//        Connection connection = ConnectionUtils.getConnection();
//
//        //创建通道
//        Channel channel = connection.createChannel();
//
//        //定义队列消费者 (3.* 方法使用，最新版已经废弃，要想使用需要降低maven相关版本)
//        QueueingConsumer consumer = new QueueingConsumer(channel);
//
//        //监听队列
//        channel.basicConsume(QUEUE_NAME, true, consumer);
//        while (true){
//            QueueingConsumer.Delivery delivery =  consumer.nextDelivery();
//            String msg = new String(delivery.getBody());
//
//            System.out.println("****收到了一条消息：" + msg);
//        }
    }
}