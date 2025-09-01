package com.shopping.backend.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.backend.entity.Users;
import com.shopping.backend.repository.UsersRepository;

import java.time.LocalDateTime;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public void insertUser(Users user) {
        System.out.println("=== UsersService.insertUser 시작 ===");
        System.out.println("입력받은 사용자 정보: " + user.getUserId() + ", " + user.getUserName() + ", " + user.getUserEmail());
        
        // 이미 존재하는 사용자인지 체크
        if (usersRepository.findByUserId(user.getUserId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        
        String password = user.getUserPassword();
        user.setUserPassword(bCryptPasswordEncoder.encode(password));
        user.setUserRole("USER");
        
        System.out.println("저장 전 사용자 정보: " + user.getUserId() + ", " + user.getUserName() + ", " + user.getUserRole());
        
        Users savedUser = usersRepository.save(user);
        System.out.println("사용자 저장 완료: " + savedUser.getUserId());
        System.out.println("=== UsersService.insertUser 종료 ===");
    }

    public Users findByUserId(String userId) {
        return usersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));
    }

    // Refresh Token 저장
    @Transactional
    public void saveRefreshToken(String userId, String refreshToken, LocalDateTime expiry) {
        Users user = findByUserId(userId);
        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(expiry);
        usersRepository.save(user);
    }

    // Refresh Token으로 사용자 찾기
    public Users findByRefreshToken(String refreshToken) {
        return usersRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("유효하지 않은 Refresh Token입니다."));
    }

    // Refresh Token 삭제 (로그아웃)
    @Transactional
    public void deleteRefreshToken(String userId, String refreshToken) {
        Users user = findByUserId(userId);
        if (user != null && user.getRefreshToken() != null && user.getRefreshToken().equals(refreshToken)) {
            user.setRefreshToken(null);
            user.setRefreshTokenExpiry(null);
            usersRepository.save(user);
        } else {
            throw new RuntimeException("일치하는 Refresh Token을 찾을 수 없거나 이미 만료되었습니다.");
        }
    }

    // 만료된 Refresh Token 정리
    public void cleanupExpiredRefreshTokens() {
        usersRepository.deleteByRefreshTokenExpiryBefore(LocalDateTime.now());
    }

    // Refresh Token 유효성 검사
    public boolean isRefreshTokenValid(String refreshToken) {
        try {
            Users user = findByRefreshToken(refreshToken);
            return user.getRefreshTokenExpiry() != null && 
                   user.getRefreshTokenExpiry().isAfter(LocalDateTime.now());
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public void updateUser(Users user) {
        usersRepository.save(user);
    }
}
