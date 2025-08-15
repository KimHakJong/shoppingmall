package com.shopping.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopping.backend.entity.Users;
import com.shopping.backend.repository.UsersRepository;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void insertUser(Users user) {
        //user 가입된 게 있는지 한번 체크
      /*  if(user.getUserId()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }*/
        String password = user.getUserPassword();
        user.setUserPassword(bCryptPasswordEncoder.encode(password));
        user.setUserRole("USER");
        usersRepository.save(user); //insert or update

    }
}
