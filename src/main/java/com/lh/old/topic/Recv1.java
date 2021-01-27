package com.lh.old.topic;

import com.rabbitmq.client.*;

public class Recv1 {

    private final static String EXCHANGE_TOPIC = "exchange_topic";



    public static void main(String[] argv) throws Exception {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // rabbit主机地址
        factory.setHost("longsan");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // rabbit host名称
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_TOPIC, BuiltinExchangeType.TOPIC);
        // 获取队列的名称
        String queueName = channel.queueDeclare().getQueue();
        // 绑定队列和交换机
        channel.queueBind(queueName, EXCHANGE_TOPIC, "*.log.*");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {

            System.out.println("body=" + new String(delivery.getBody(), "utf-8"));
        };
        // 自动确认
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}