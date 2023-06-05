package com.me.logisticservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class LogisticServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogisticServiceApplication.class, args);
	}

}
