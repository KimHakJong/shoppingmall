package com.shopping.backend.controller;

import com.shopping.backend.entity.Users;
import com.shopping.backend.repository.UsersRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    @Autowired
    private UsersRepository usersRepository;

    @PostMapping("/join")
    public String joinMember(@RequestBody Users user) {
        // JPA를 사용하여 회원 정보 저장
        usersRepository.insertUser(user);
        return "회원가입이 정상적으로 완료되었습니다.";
    }
} 