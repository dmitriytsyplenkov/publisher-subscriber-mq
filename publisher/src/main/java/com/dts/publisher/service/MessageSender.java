package com.dts.publisher.service;

import com.dts.publisher.model.ActionType;
import com.dts.publisher.model.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
@Slf4j
public class MessageSender {
    @Value("${spring.task.scheduling.pool.size}")
    private int poolSize;
    @Value("${publisher.message.generation.delay}")
    private long delay;
    @Value("${server.port}")
    private String serverPort;
    private final ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private AtomicLong idVal = new AtomicLong(1);

    @Bean
    Random randomGenerator() {
        return new Random();
    }

    public MessageSender(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }


    @PostConstruct
    public void sendRandomMessage() {
        for (int i = 0; i < poolSize; i++) {
            threadPoolTaskScheduler.scheduleWithFixedDelay(()->{
                MqMessage generatedMessage = generateRandomMessage();
                restTemplate().exchange("http://localhost:"+serverPort+"/send", HttpMethod.POST,new HttpEntity<>(generatedMessage),String.class);
                log.info("sending msg " + generatedMessage);
            }, delay);

        }

    }

    public MqMessage generateRandomMessage() {
        return MqMessage.builder()
                .id(idVal.getAndIncrement())
                .msisdn(randomGenerator().nextLong())
                .timestamp(new Date())
                .action(ActionType.values()[randomGenerator().nextInt(ActionType.values().length)])
                .build();
    }

}
