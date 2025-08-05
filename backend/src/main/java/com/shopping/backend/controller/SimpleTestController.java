package com.shopping.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 간단한 테스트 컨트롤러
 */
@RestController
public class SimpleTestController {

    @GetMapping("/api/simple/test")
    public String simpleTest() {
        return "Simple test endpoint is working!";
    }
} 