package com.dts.publisher.controller;

import com.dts.publisher.model.ActionType;
import com.dts.publisher.model.MqMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class PublisherController {

    private final RabbitTemplate rabbitTemplate;

    public PublisherController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    @PostMapping("/send")
    public ResponseEntity<String> sendMessage(@RequestBody MqMessage mqMessage,
                                      @Value("${mq.settings.exchange.name}") String exchangeName,
                                      @Value("${mq.settings.purchase-key}") String purchaseRoutingKey,
                                      @Value("${mq.settings.subscription-key}") String subscriptionRoutingKey) {
        if (mqMessage != null) {
            if (mqMessage.getAction() == ActionType.PURCHASE) {
                 rabbitTemplate.convertAndSend(exchangeName, purchaseRoutingKey, mqMessage);
            } else if (mqMessage.getAction() == ActionType.SUBSCRIPTION) {
                rabbitTemplate.convertAndSend(exchangeName, subscriptionRoutingKey, mqMessage);
            } else {
                log.error("unknown action type detected" + mqMessage.getAction());
                return new ResponseEntity<>("unknown action type!!!", HttpStatus.BAD_REQUEST);
            }
        }

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

}
