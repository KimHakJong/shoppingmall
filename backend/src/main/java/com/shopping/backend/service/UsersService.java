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

    public int insertUser(Users user) {
        int result = 0;
        user = usersRepository.save(user); //insert or update
        if (!(user==null)) {
            result = 1;
        }
        return result;
    }
}
