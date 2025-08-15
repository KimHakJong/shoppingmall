package com.shopping.backend;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jira")
public class Test {

    @PostMapping("/created")
    public ResponseEntity<String> handleCreated(@RequestBody String payload) {
        System.out.println("Received webhook payload:");
        System.out.println(payload);

        return ResponseEntity.ok("Webhook received");
    }

}
