package org.example.user.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.example.user.model.User;
import org.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenParser {
    @Autowired
    private UserRepository userRepository;

    public User parse(String token) {
        Claims claims = Jwts.parser().setSigningKey("secretKey").parseClaimsJws(token).getBody();
        UUID userId = UUID.fromString(claims.get("userId", String.class));
        return userRepository.findById(userId).orElseThrow(RuntimeException::new);
    }
}
