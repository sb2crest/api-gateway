package com.fdapn.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloWorldController {
    @GetMapping("/user")
    public String helloUser(){
        return "HELLO USER";
    }

    @GetMapping("/admin")
    public String helloAdmin(){
        return "HELLO ADMIN";
    }

    @GetMapping("/manager")
    public String helloManager(){
        return "HELLO MANAGER";
    }
}
