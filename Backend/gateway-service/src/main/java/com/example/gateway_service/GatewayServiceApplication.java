package com.example.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients  // Enable this if you plan to make internal calls via Feign
public class GatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("user_service", r -> r
						.path("/users/**")
						.uri("lb://user-service"))
				.route("leaderboard_service", r -> r.path("/leaderboards/**")
						.uri("lb://leaderboard-service"))
				.route("history_service", r -> r
						.path("/score-history/**")
						.uri("lb://history-service"))
				.build();
	}
}
