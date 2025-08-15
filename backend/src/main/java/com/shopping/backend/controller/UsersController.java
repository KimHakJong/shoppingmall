package com.shopping.backend.controller;

import com.shopping.backend.dto.LoginRequest;
import com.shopping.backend.dto.LoginResponse;
import com.shopping.backend.entity.Users;
import com.shopping.backend.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinUser(@RequestBody Users user) {

        try {
            usersService.insertUser(user);
            return ResponseEntity.ok("회원가입이 정상적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("회원가입에 실패하였습니다."); // TODO: handle exception
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> loginUser (@RequestBody LoginRequest request) {

        return ResponseEntity.ok(null);
    }
}