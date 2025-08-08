package com.shopping.backend.controller;

import com.shopping.backend.entity.Users;
import com.shopping.backend.service.UsersService;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersService usersService;

    @PostMapping("/join")
    public String joinUser (@RequestBody Users user) {

        // JPA를 사용하여 회원 정보 저장
        int success = usersService.insertUser(user);
        if (success==1) {
            return "회원가입이 정상적으로 완료되었습니다.";
        }
            return "실패";
    }
} 