package com.wealthmanager.service.impl;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockPriceConsumer {

    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "stock-price-topic", groupId = "portfolio-group")
    public void consume(String message) {
           System.out.println("Received from Kafka: " + message);

        messagingTemplate.convertAndSend(
                "/topic/portfolio",
                message
        );

    }

}