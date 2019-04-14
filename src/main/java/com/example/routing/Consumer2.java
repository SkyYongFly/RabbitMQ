package com.example.routing;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * 路由模式消费者2：产生一个队列，绑定到交换机，从队列中获取指定路由键类型的消息
 */
public class Consumer2 {
    //定义交换机名称
    private static final String EXCHANGE_NAME = "test_exchange_direct";
    //设置消息的队列名称，例如发送邮件的队列
    private static final String QUEUE_NAME = "test_queue_direct_2";

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //获取连接
        Connection connection = ConnectionUtils.getConnection();
        //创建频道
        final Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");    //指定队列路由键
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning"); //指定队列路由键
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");   //指定队列路由键

        //保证队列一次只分发一个
        channel.basicQos(1);

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body, "UTF-8");
                System.out.println("****收到了一条消息：" + msg);

                try {
                    //模拟业务耗时操作
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    System.out.println(msg + "：处理完成");
                    //处理完成手动应答
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }
            }
        };

        //监听队列
        boolean autoAck = false; //自动应答关闭
        channel.basicConsume(QUEUE_NAME, autoAck,consumer);
    }
}