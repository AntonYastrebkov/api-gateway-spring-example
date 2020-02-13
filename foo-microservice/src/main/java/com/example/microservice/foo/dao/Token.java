package com.example.microservice.foo.dao;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@Accessors(chain = true)
public class Token {
    private String accessToken;
    private String tokenType;
    private String refreshToken;
    private String scope;
}
