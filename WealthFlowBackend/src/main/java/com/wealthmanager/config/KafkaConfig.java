package com.wealthmanager.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic stockTopic() {
        return new NewTopic("stock-price-topic", 1, (short) 1);
    }

}