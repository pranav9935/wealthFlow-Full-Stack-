package com.wealthmanager.service.impl;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StockPriceProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendStockPrice(String message) {

         System.out.println("Sending message to Kafka: " + message);

        kafkaTemplate.send("stock-price-topic", message);

    }

}