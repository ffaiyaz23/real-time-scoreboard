package com.example.gateway_service.filter;

import com.example.gateway_service.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, org.springframework.cloud.gateway.filter.GatewayFilterChain chain) {
        // Retrieve the Authorization header
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            try {
                // Try to extract the username (this will throw if token is invalid)
                String username = jwtUtil.extractUsername(token);
                // (Optional) You might add additional checks here.
            } catch (Exception e) {
                // If token is invalid, return a 401 Unauthorized response
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        } else {
            // If no token is provided, you might want to enforce authentication.
            // For demonstration, we let it pass; adjust according to your security needs.
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -1; // Ensure this filter runs early
    }
}
