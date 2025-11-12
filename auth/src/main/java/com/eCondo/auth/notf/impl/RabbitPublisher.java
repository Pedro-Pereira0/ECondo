package com.eCondo.auth.notf.impl;

import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.eCondo.auth.api.events.UserCreatedEvent;
import com.eCondo.auth.notf.EventPublisher;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitPublisher implements EventPublisher{

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbitmq.userExchange}")
    private String userExchange;

    @Value("${app.rabbitmq.userCreatedRoutingKey}")
    private String userCreatedKey;
    
    @Override
    public void send(UserCreatedEvent event) {
            rabbitTemplate.convertAndSend(userExchange, userCreatedKey, event, msg -> {
            msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
            return msg;
        });
    }
    
}
