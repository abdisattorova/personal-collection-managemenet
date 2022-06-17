package com.itransition.itransitioncoursework.controller;
//Sevinch Abdisattorova 06/17/2022 11:12 AM


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RequestMapping("/admin")
@RestController
public class Test {

    @GetMapping
    public String sayHello() {
        return "welcome";
    }
}
