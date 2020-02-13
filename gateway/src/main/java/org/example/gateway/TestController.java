package org.example.gateway;

import org.example.gateway.client.AuthenticationClient;
import org.example.gateway.model.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private AuthenticationClient client;

    @GetMapping("/get/token")
    public AuthenticationToken getToken() {
        return client.getToken();
    }
}
