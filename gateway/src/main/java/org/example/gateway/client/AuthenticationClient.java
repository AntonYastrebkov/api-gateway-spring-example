package org.example.gateway.client;

import org.example.gateway.model.AuthenticationToken;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "http://localhost:8081", name = "foo-service")
public interface AuthenticationClient {
    @GetMapping("/token")
    AuthenticationToken getToken();

    @GetMapping("/check")
    String checkToken(@RequestParam String token);
}
