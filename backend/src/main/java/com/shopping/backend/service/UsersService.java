package com.shopping.backend.service;

import org.springframework.stereotype.Service;

import com.shopping.backend.entity.Users;
import com.shopping.backend.repository.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void insertUser(Users user) {
        //user 가입된 게 있는지 한번 체크
        if(user.getUserId()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        usersRepository.save(user); //insert or update

    }
}
