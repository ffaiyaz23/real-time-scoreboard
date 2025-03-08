package com.example.leaderboard_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients   // Enables OpenFeign for inter-service calls
public class LeaderboardServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(LeaderboardServiceApplication.class, args);
	}
}
