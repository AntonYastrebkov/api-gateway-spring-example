package org.example.user.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.example.user.model.JwtTokenProperties;
import org.example.user.model.Token;
import org.example.user.model.TokenSubject;
import org.example.user.model.User;
import org.example.user.model.UserDto;
import org.example.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class TokenGenerator {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenProperties tokenProperties;
    private final ObjectMapper mapper = new ObjectMapper();

    public Token generateToken(UserDto userDto) {
        User user = userRepository.findByEmail(userDto.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("No user with email " + userDto.getEmail()));
        TokenSubject tokenSubject = new TokenSubject()
                .setEmail(user.getUsername())
                .setUserId(user.getId())
                .setScope("scope")
                .setAuthorities(user.getAuthorities());

        return new Token()
                .setAccessToken(generateJwt(tokenSubject, tokenProperties.getAccessExpiration()))
                .setRefreshToken(generateJwt(tokenSubject, tokenProperties.getRefreshExpiration()))
                .setTokenType("Bearer")
                .setScope("scope");
    }

    private String generateJwt(TokenSubject tokenSubject, int expiration) {
        Instant now = Instant.now();
        Date expiryDate = Date.from(now.plusMillis(expiration));
        tokenSubject.setExpiration(Instant.now().plusMillis(expiration).toEpochMilli());
        String subject;
        try {
            subject = mapper.writeValueAsString(tokenSubject);
        } catch (JsonProcessingException ex) {
            System.out.println("¯\\_(ツ)_/¯");
            throw new RuntimeException("Unable to generate JWT");
        }

        return Jwts.builder()
                .setPayload(subject)
//                .setId(tokenSubject.getUserId().toString())
//                .setSubject(subject)
//                .setIssuedAt(Date.from(now))
//                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, tokenProperties.getJwtSecret())
                .compact();
    }
}
