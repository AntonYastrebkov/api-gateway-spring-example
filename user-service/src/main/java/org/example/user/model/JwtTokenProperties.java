package org.example.user.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtTokenProperties {
    private String jwtSecret;
    private int accessExpiration;
    private int refreshExpiration;
}
