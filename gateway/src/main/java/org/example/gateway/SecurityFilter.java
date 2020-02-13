package org.example.gateway;

import org.example.gateway.client.AuthenticationClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.commons.httpclient.OkHttpClientFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class SecurityFilter implements GatewayFilter {
    @Autowired
    private AuthenticationClient client;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Security filter");
        List<String> authorization = exchange.getRequest().getQueryParams().get("Authorization");
        if (authorization == null || authorization.isEmpty()) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
            // return chain.filter(exchange);
        }
        String token = authorization.get(0);
        System.out.println("Try to check token: " + token);
        String answer = client.checkToken(token);
        System.out.println(answer);
        if (!"true".equals(answer)) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
        System.out.println("Token is verified: " + token);
        return chain.filter(exchange);
    }
}
