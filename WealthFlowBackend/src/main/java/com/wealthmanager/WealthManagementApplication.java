package com.wealthmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
@EnableScheduling
@SpringBootApplication
@EnableKafka
@EnableAsync
public class WealthManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(WealthManagementApplication.class, args);
	}

}
