package com.example.history_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // Enable this if you plan to call this service via Feign from others
public class HistoryServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(HistoryServiceApplication.class, args);
	}
}
