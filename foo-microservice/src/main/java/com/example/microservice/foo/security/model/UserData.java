package com.example.microservice.foo.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserData {
    private String name;
    private UUID id;
}
