package com.shopping.backend.repository;

import com.shopping.backend.entity.Users;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    // 기본적인 save(), findById(), deleteById() 등은 자동으로 제공됨

    Optional<Users> findByUserId(String UserId);
    
    // Refresh Token으로 사용자 찾기
    Optional<Users> findByRefreshToken(String refreshToken);
    
    // 만료된 Refresh Token 삭제
    void deleteByRefreshTokenExpiryBefore(LocalDateTime expiry);

}