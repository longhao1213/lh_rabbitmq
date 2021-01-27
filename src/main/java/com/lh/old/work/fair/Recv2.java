package com.lh.old.work.fair;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Recv2 {

    private final static String QUEUE_NAME = "work_fair";

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

        /**
         * 限制消费者每次只接收一条,消费完成后再去接收下一条
         */
        channel.basicQos(1);

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

//        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
//            String message = new String(delivery.getBody(), "UTF-8");
//            System.out.println(" [x] Received '" + message + "'");
//        };

        DefaultConsumer defaultConsumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {

                // 模拟消费者处理性能很慢
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // consumerTag 是固定的 可以做此会话的名字， deliveryTag 每次接收消息+1
                System.out.println("consumerTag消息标识=" + consumerTag);
                //可以获取交换机，路由健等
                System.out.println("envelope元数据=" + envelope);
                System.out.println("properties配置信息=" + properties);
                System.out.println("body=" + new String(body, "utf-8"));

                // 手动确认消息,并且单条确认
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };

//        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
        // 关闭消息的自动确认
        channel.basicConsume(QUEUE_NAME, false, defaultConsumer);
    }
}