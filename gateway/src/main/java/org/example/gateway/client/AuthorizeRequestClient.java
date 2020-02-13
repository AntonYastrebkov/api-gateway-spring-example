package org.example.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:9000", name = "user-service")
public interface AuthorizeRequestClient {

    @PostMapping("/verify")
    Boolean verifyToken(@RequestParam String token);
}
