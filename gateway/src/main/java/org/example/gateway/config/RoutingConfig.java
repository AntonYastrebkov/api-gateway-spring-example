package org.example.gateway.config;

import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.example.gateway.CustomFilter;
import org.example.gateway.SecurityFilter;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
    @Autowired
    private CustomFilter filter;
    @Autowired
    private SecurityFilter securityFilter;

    private ObjectFactory<HttpMessageConverters> messageConverters = HttpMessageConverters::new;

    @Bean
    Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

    @Bean
    Decoder feignFormDecoder() {
        return new SpringDecoder(messageConverters);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder build) {
        System.out.println("Starting");
        return build.routes()
//                .route("foo-service",
//                        r -> r.path("/foo**")
//                                .uri("lb://foo-service")
//                )
                .route(r -> r.path("/foo/**")
                        .uri("lb://foo-service")
                        // .filter(filter)
                        .filter(securityFilter))
                .build();
    }
}
