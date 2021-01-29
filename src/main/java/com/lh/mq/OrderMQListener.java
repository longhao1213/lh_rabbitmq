package com.lh.mq;

import com.lh.config.RabbitMQConfig;
import com.lh.config.RabbitMQDeadConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Copyright (C), 2006-2010, ChengDu ybya info. Co., Ltd.
 * FileName: OrderMQListener.java
 *
 * @author lh
 * @version 1.0.0
 * @Date 2021/01/27 17:23
 */
//@Component
//@RabbitListener(queues = RabbitMQDeadConfig.NEW_MERCHANT_QUEUE)
public class OrderMQListener {
    private final static Logger logger = LoggerFactory.getLogger(OrderMQListener.class);

    @RabbitHandler
    public void messageHandler(String body, Message message, Channel channel) throws IOException {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        logger.info("msgTag"+msgTag);
        logger.info("message"+message.toString());
        logger.info("body"+body);

        // 告诉broker 消息已经被消费
//        channel.basicAck(msgTag, false);

        // 拒收消息 并且让消息从新回到队列
//        channel.basicNack(msgTag, false, true);
    }
}