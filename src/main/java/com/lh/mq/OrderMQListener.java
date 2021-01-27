package com.lh.mq;

import com.lh.config.RabbitMQConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Copyright (C), 2006-2010, ChengDu ybya info. Co., Ltd.
 * FileName: OrderMQListener.java
 *
 * @author lh
 * @version 1.0.0
 * @Date 2021/01/27 17:23
 */
@Component
@RabbitListener(queues = RabbitMQConfig.QUEUE)
public class OrderMQListener {
    private final static Logger logger = LoggerFactory.getLogger(OrderMQListener.class);

    @RabbitHandler
    public void messageHandler(String body, Message message) {
        long msgTag = message.getMessageProperties().getDeliveryTag();
        logger.info("msgTag"+msgTag);
        logger.info("message"+message.toString());
        logger.info("body"+body);
    }
}