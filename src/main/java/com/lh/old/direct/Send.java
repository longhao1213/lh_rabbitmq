package com.lh.old.direct;


import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * Copyright (C), 2006-2010, ChengDu ybya info. Co., Ltd.
 * FileName: Send.java
 *
 * @author lh
 * @version 1.0.0
 * @Date 2021/01/26 16:15
 */
public class Send {
    private final static String EXCHANGE_DIRECT = "exchange_direct";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接
        ConnectionFactory factory = new ConnectionFactory();
        // rabbit主机地址
        factory.setHost("longsan");
        factory.setUsername("admin");
        factory.setPassword("123456");
        // rabbit host名称
        factory.setVirtualHost("/dev");
        factory.setPort(5672);

        try (   // 创建连接
                Connection connection = factory.newConnection();
                // 创建信道
                Channel channel = connection.createChannel()) {
            // 创建队列

            // 绑定交换机
            channel.exchangeDeclare(EXCHANGE_DIRECT, BuiltinExchangeType.DIRECT);

            String error = "error日志";
            String info = "info日志";
            String debug = "debug日志";

            channel.basicPublish(EXCHANGE_DIRECT, "errorRoutingKey", null, error.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_DIRECT, "infoRoutingKey", null, info.getBytes(StandardCharsets.UTF_8));
            channel.basicPublish(EXCHANGE_DIRECT, "debugRoutingKey", null, debug.getBytes(StandardCharsets.UTF_8));

            System.out.println("路由模式所有消息发送完成");
        }

    }
}