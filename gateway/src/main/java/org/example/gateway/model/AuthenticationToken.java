package org.example.gateway.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationToken {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String scope;
}
