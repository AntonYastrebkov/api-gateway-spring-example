package com.example.microservice.foo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Foo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String info;

    public Foo(String info) {
        this.info = info;
    }
}
