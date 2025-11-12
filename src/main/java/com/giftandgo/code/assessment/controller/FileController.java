package com.giftandgo.code.assessment.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/giftandgo")
public class FileController {

    @GetMapping("/test")
    public Greeting test() {
        return new Greeting("Hello", "RESTful World!");
    }

}

record Greeting(String salutation, String target) {
}