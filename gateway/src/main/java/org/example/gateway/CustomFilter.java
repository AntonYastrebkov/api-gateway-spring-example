package org.example.gateway;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class CustomFilter implements GatewayFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Filtering");
        exchange.getRequest().getQueryParams().forEach((n, v) -> {
            System.out.print(n + ": ");
            v.forEach(e -> System.out.print(e + " "));
            System.out.println();
        });
        List<String> token = exchange.getRequest().getQueryParams().get("flag");
        if (token.contains("stop")) {
            System.out.println("Request rejected");
            return Mono.error(new Exception("Go away"));
        }
        return chain.filter(exchange);
    }
}
