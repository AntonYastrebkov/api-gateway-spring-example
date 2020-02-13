package com.example.microservice.foo.security;

import com.example.microservice.foo.security.model.JwtAuthentication;
import com.example.microservice.foo.security.model.UserData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String jwt = jwtAuthentication.getJwt();
        try {
            Claims jwtClaims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(jwt).getBody();
            String username = jwtClaims.get("email", String.class);
            String userId = jwtClaims.get("userId", String.class);
            jwtAuthentication.setUserData(new UserData(username, UUID.fromString(userId)));
            jwtAuthentication.setPrincipal(userId::toString);
        } catch (Exception ex) {
            throw new RuntimeException("User unauthorized");
        }

        return jwtAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
