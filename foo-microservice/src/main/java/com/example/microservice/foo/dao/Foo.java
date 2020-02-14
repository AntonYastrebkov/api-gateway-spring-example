package com.example.microservice.foo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String info;
    private UUID userId;

    public Foo(String info, UUID userId) {
        this.info = info;
        this.userId = userId;
    }
}
