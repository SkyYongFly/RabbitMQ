package com.example.tx;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.example.simple.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 *  事务管理
 */
public class Producer {
    //定义队列名称
    private static final String QUEUE_NAME = "test_queue_transaction";

    public static void main(String[] args) throws IOException, TimeoutException {
        //获取一个连接
        Connection connection = ConnectionUtils.getConnection();

        //从连接中获取一个通道
        Channel channel = connection.createChannel();
        //创建队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        try{
            //开启事务
            channel.txSelect();

            int i = 1/0;
            //定义要发送的消息
            String msg = "Hello Transaction!";
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            System.out.println("----发送了一条消息：" + msg);

            //提交事务
            channel.txCommit();
        }catch (Exception e){
            //事务回滚
            channel.txRollback();
            System.out.println("产生异常，消息未成功发送！");
        }

        //关闭资源连接
        channel.close();
        connection.close();
    }
}