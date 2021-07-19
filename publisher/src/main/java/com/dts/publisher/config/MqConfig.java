package com.dts.publisher.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${mq.settings.purchase.queue.name:default.purchase.queue.name}")
    private String purchaseQueueName;
    @Value("${mq.settings.subscription.queue.name:default.subscription.queue.name}")
    private String subscriptionQueueName;
    @Value("${mq.settings.exchange.name:default.exchange.name}")
    private String exchangeName;
    @Value("${mq.settings.purchase.key:default.purchase.key}")
    private String purchaseRoutingKey;

    @Value("${mq.settings.subscription.key:default.subscription.key}")
    private String subscriptionRoutingKey;

    @Bean
    public Declarables topicBindings() {
        Queue purchaseQueue = new Queue(purchaseQueueName, true);
        Queue subscriptionQueue = new Queue(subscriptionQueueName, true);
        TopicExchange exchange = new TopicExchange(exchangeName);
        return new Declarables(purchaseQueue,
                subscriptionQueue,
                exchange,
                BindingBuilder.bind(purchaseQueue).to(exchange).with(purchaseRoutingKey),
                BindingBuilder.bind(subscriptionQueue).to(exchange).with(subscriptionRoutingKey));
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter());
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
