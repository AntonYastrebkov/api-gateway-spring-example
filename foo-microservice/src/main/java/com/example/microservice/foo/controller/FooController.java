package com.example.microservice.foo.controller;

import com.example.microservice.foo.dao.Foo;
import com.example.microservice.foo.dao.FooRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("isAuthenticated()")
public class FooController {
    @Autowired
    private FooRepository fooRepository;

    @GetMapping("/foo/{id}")
    public Foo getFoo(@PathVariable Long id) {
        return fooRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @PostMapping("/foo")
    public Foo postFoo(@RequestParam String info) {
        return fooRepository.save(new Foo(info));
    }
}
