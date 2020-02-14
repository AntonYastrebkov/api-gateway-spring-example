package com.example.microservice.foo.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserInfo {
    private String email;
    private UUID id;
    private String additionalInfo;
}
