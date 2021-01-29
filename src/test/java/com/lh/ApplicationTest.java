package com.lh;

import com.lh.config.RabbitMQConfig;
import com.lh.config.RabbitMQDeadConfig;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
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
//        for (int i = 0; i < 5; i++) {
            rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "order.new", "新订单");
//        }
    }

    @Test
    void testConfirmCallback() {
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            /**
             *
             * @param correlationData 配置
             * @param ack 交换机是否收到消息,true成功
             * @param cause 失败的原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("correlationData:" + correlationData);
                System.out.println("ack:" + ack);
                System.out.println("cause:" + cause);
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME+"wefwa", "order.new", "新订单");
    }

    @Test
    void testReturnCallback() {
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                System.out.println(returnedMessage.toString());
            }
        });
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, "wfafe.order.new", "新订单");
    }

    @Test
    void testSendMessageAndToDeadQueue() {
        rabbitTemplate.convertAndSend(RabbitMQDeadConfig.NEW_MERCHANT_EXCHANGE,RabbitMQDeadConfig.NEW_MERCHANT_ROUTING_KEY,"这个消息会进入死信队列");
    }
}