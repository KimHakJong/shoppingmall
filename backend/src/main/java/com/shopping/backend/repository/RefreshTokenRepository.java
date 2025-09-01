package com.shopping.backend.repository;

import com.shopping.backend.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.time.LocalDateTime;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByUserId(String userId);
    Optional<RefreshToken> findByTokenValue(String tokenValue);
    void deleteByUserId(String userId);
    void deleteByTokenValue(String tokenValue);
    void deleteByExpiryDateBefore(LocalDateTime dateTime);
}

