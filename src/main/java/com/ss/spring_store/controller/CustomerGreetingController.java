package com.ss.spring_store.controller;

import com.ss.spring_store.model.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/v1")
public class CustomerGreetingController {
    private static final String template = "Hello, %s %s!";
    private final AtomicLong counter = new AtomicLong();
    @GetMapping("/greet")
    public  Greeting greet(@RequestParam(   value = "gender", defaultValue = "0") Boolean gender,
                            @RequestParam(value = "userName", defaultValue = "User") String userName)   {
        return Greeting.builder()
                .id(counter.incrementAndGet())
                .content(String.format(template, gender ? "Mr." : "Ms.", " " + userName))
                .build();
    }
}
