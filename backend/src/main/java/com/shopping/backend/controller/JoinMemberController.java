package com.shopping.backend.controller;


@RestController
@RequestMapping("/api/member")
public class SimpleTestController {

    @GetMapping("/join")
    public String health() {
        return "Shopping Mall Backend is running!";
    }
} 