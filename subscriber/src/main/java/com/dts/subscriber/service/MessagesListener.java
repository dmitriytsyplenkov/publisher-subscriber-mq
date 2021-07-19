package com.dts.subscriber.service;

import com.dts.subscriber.model.Purchase;
import com.dts.subscriber.model.Subscription;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MessagesListener {
    private final SubscriptionService subscriptionService;
    private final PurchaseService purchaseService;

    public MessagesListener(SubscriptionService subscriptionService, PurchaseService purchaseService) {
        this.subscriptionService = subscriptionService;
        this.purchaseService = purchaseService;
    }


    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = "${mq.settings.purchase.queue.name:default.purchase.queue.name}",durable = "true"),
            exchange = @Exchange(value = "${mq.settings.exchange.name:default.exchange.name}",type = "topic"),
            key="${mq.settings.purchase.key:default.purchase.key}")
    )
    public void handlePurchase(Purchase purchase) {
        log.info("Handle purchase message!!!" + purchase.toString());
        purchaseService.savePurchase(purchase);
    }

    @RabbitListener(bindings = @QueueBinding(
            value=@Queue(name = "${mq.settings.subscription.queue.name:default.subscription.queue.name}", durable = "true"),
            exchange = @Exchange(value = "${mq.settings.exchange.name:default.exchange.name}",type = "topic"),
            key="${mq.settings.subscription.key:default.subscription.key}")
    )
    public void handleSubscription(Subscription subscription) {
        log.info("Handle subscription message!!!" + subscription.toString());
        subscriptionService.saveSubscription(subscription);
    }


}
