package org.example.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder build) {
        System.out.println("Starting");
        return build.routes()
                .route("foo-service",
                        r -> r.path("/foo**")
                                .uri("lb://foo-service")
                )
                .route(r -> r.path("/foo/**").uri("lb://foo-service").filter(new CustomFilter()))
                .build();
    }
}
