package com.lh.config;


import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Copyright (C), 2006-2010, ChengDu ybya info. Co., Ltd.
 * FileName: RabbitMQDeadConfig.java
 *
 * @author lh
 * @version 1.0.0
 * @Date 2021/01/28 10:35
 */
@Configuration
public class RabbitMQDeadConfig {
    /**
     * 死信队列
     */
    public static final String LOCK_MERCHANT_DEAD_QUEUE = "lock_merchant_dead_queue";

    /**
     * 死信交换机
     */
    public static final String LOCK_MERCHANT_DEAD_EXCHANGE = "lock_merchant_dead_exchange";

    /**
     * 死信队列的路由key
     */
    public static final String LOCK_MERCHANT_ROUTING_KEY = "lock_merchant_routing_key";

    /**
     * 创建死信交换机
     */
    @Bean
    public Exchange lockMerchantDeadExchange() {
        return ExchangeBuilder.topicExchange(LOCK_MERCHANT_DEAD_EXCHANGE).durable(false).build();
    }

    /**
     * 创建死信队列
     * @return
     */
    @Bean
    public Queue lockMerchantDeadQueue() {
        return QueueBuilder.durable(LOCK_MERCHANT_DEAD_QUEUE).build();
    }

    /**
     * 绑定死信交换机和死信队列
     * @return
     */
    @Bean
    public Binding lockMerchantBinding() {
        return new Binding(LOCK_MERCHANT_DEAD_QUEUE, Binding.DestinationType.QUEUE, LOCK_MERCHANT_DEAD_EXCHANGE, LOCK_MERCHANT_ROUTING_KEY, null);
    }


    // ======= 下面开始创建普通的队列,然后绑定到死信交换机
    public static final String NEW_MERCHANT_QUEUE = "new_merchant_queue";

    public static final String NEW_MERCHANT_EXCHANGE = "new_merchant_exchange";

    public static final String NEW_MERCHANT_ROUTING_KEY = "new_merchant_key";
    @Bean
    public Exchange newMerchantExchange() {
        return ExchangeBuilder.topicExchange(NEW_MERCHANT_EXCHANGE).durable(false).build();
    }
    @Bean
    public Queue newMerchantQueue() {
        Map<String, Object> args = new HashMap<>(3);
        // 死信交换机名称
        args.put("x-dead-letter-exchange", LOCK_MERCHANT_DEAD_EXCHANGE);
        // 进入到死信交换机后的路由key
        args.put("x-dead-letter-routing-key", LOCK_MERCHANT_ROUTING_KEY);
        // 过期时间,单位毫秒
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(NEW_MERCHANT_QUEUE).withArguments(args).build();
    }
    @Bean
    public Binding newMerchantBinding() {
        return new Binding(NEW_MERCHANT_QUEUE, Binding.DestinationType.QUEUE, NEW_MERCHANT_EXCHANGE, NEW_MERCHANT_ROUTING_KEY, null);
    }

}