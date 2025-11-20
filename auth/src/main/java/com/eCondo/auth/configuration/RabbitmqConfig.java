package com.eCondo.auth.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.NonNull;

@Configuration
@EnableRabbit
public class RabbitmqConfig {

    @Value("${app.rabbitmq.userExchange}")
    private String userExchange;

    @Value("${app.rabbitmq.userCreatedQueue}")
    private String createdQueue;

    @Value("${app.rabbitmq.userCreatedRoutingKey}")
    private String userCreatedKey;


    @Value("${app.rabbitmq.userCreatedRoutingKey}")
    private String userUpdatedKey;

    @Value("${app.rabbitmq.userUpdatedQueue}")
    private String updatedQueue;

    @Value("create.user.queue")
    private String createUserQueue;

    @Value("create.user")
    private String createUserKey;

    @Value("${app.instance}")
    private String instance;

    
    @Bean
    public TopicExchange userExchange() {
        return new TopicExchange(userExchange);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(@NonNull ConnectionFactory connectionFactory, RabbitTemplateConfigurer configurer) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        configurer.configure(rabbitTemplate, connectionFactory); // applies Spring Boot's configurations
        rabbitTemplate.setExchange(userExchange);
        return rabbitTemplate;
    }

    @Bean
    public Queue userCreatedQueue() {
        String uniqueQueueName = createdQueue + "." + instance;
        return new Queue(uniqueQueueName, true, false, false);
    }

    @Bean
    public Queue userUpdatedQueue() {
        String uniqueQueueName = updatedQueue + "." + instance;
        return new Queue(uniqueQueueName, true, false, false);
    }

    @Bean
    public Queue createUserQueue() {
        String uniqueQueueName = createUserQueue + "." + instance;
        return new Queue(uniqueQueueName, true, false, false);
    }


    @Bean
    public Binding createUserBinding(Queue createUserQueue, TopicExchange userExchange){
        return BindingBuilder.bind(createUserQueue).to(userExchange).with(createUserKey);
    }

    @Bean
    public Binding userCreatedBinding(Queue userCreatedQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userCreatedQueue).to(userExchange).with(userCreatedKey);
    }
    @Bean
    public Binding userUpdatedBinding(Queue userUpdatedQueue, TopicExchange userExchange) {
        return BindingBuilder.bind(userUpdatedQueue).to(userExchange).with(userUpdatedKey);
    }


    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
}
