package com.dts.publisher.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqConfig {

    @Value("${mq.settings.queue.name}")
    private String queueName;
    @Value("${mq.settings.exchange.name}")
    private String exchangeName;
    @Value("${mq.settings.purchase-key}")
    private String purchaseRoutingKey;

    @Value("${mq.settings.subscription-key}")
    private String subscriptionRoutingKey;

    @Bean
    public Declarables topicBindings() {
        Queue queue = new Queue(queueName, false);
        TopicExchange exchange = new TopicExchange(exchangeName);
        return new Declarables(queue,
                exchange,
                BindingBuilder.bind(queue).to(exchange).with(purchaseRoutingKey),
                BindingBuilder.bind(queue).to(exchange).with(subscriptionRoutingKey));
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
