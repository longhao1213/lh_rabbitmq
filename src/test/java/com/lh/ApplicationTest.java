package com.lh;

import com.lh.config.RabbitMQConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApplicationTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    void testSend() {
        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.new", "新订单" + i);
        }
    }
}