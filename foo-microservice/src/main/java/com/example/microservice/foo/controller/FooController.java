package com.example.microservice.foo.controller;

import com.example.microservice.foo.dao.Foo;
import com.example.microservice.foo.dao.FooRepository;
import com.example.microservice.foo.dao.UserInfo;
import com.example.microservice.foo.security.model.JwtAuthentication;
import com.netflix.discovery.EurekaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class FooController {
    @Autowired
    private FooRepository fooRepository;
    @Autowired
    private EurekaClient eurekaClient;
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/foo/{id}")
    public Foo getFoo(@PathVariable Long id, Principal principal, @AuthenticationPrincipal Authentication authentication) {
        System.out.println("PRINCIPAL: " + principal.toString());
        System.out.println(((JwtAuthentication)principal).getUserData().getId());
        System.out.println("AUTHENTICATION: " + authentication.toString());
        return fooRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/foo")
    public Foo postFoo(
            @RequestParam String info,
            @AuthenticationPrincipal Authentication authentication
    ) {
        return fooRepository.save(
                new Foo(info, ((JwtAuthentication)authentication).getUserData().getId()));
    }

    @GetMapping("/user")
    public UserInfo getUser(@AuthenticationPrincipal Authentication authentication) {
        String userServiceUri = eurekaClient
                .getNextServerFromEureka("user-service", false)
                .getHomePageUrl();
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtAuthentication.getJwt());
        System.out.println(headers.toString());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        return restTemplate.exchange(
                userServiceUri + "/user/me",
                HttpMethod.GET,
                entity, UserInfo.class).getBody();
    }
}
