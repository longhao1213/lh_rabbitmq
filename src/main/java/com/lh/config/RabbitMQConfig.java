package com.lh.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Copyright (C), 2006-2010, ChengDu ybya info. Co., Ltd.
 * FileName: RabbitMQConfig.java
 *
 * @author lh
 * @version 1.0.0
 * @Date 2021/01/27 17:12
 */
//@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE_NAME = "order_exchange";

    public static final String QUEUE = "order_queue";

    /**
     * 交换机
     * @return
     */
    @Bean
    public Exchange orderExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    /**
     * 队列
     */
    @Bean
    public Queue orderQueue() {
        return QueueBuilder.durable(QUEUE).build();
    }

    /**
     * 交换机和队列的绑定关系
     */
    @Bean
    public Binding orderBinding(Queue queue, Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("order.#").noargs();
    }
}