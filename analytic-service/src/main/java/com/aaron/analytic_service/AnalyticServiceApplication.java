package com.aaron.analytic_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class AnalyticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyticServiceApplication.class, args);
	}

}
