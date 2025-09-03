package com.shopping.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.shopping.backend.entity.OAuthUsers;
import com.shopping.backend.repository.OAuthUsersRepository;

@Service
public class OAuthUsersService {
    private final OAuthUsersRepository oauthUsersRepository;

    public OAuthUsersService(OAuthUsersRepository oauthUsersRepository) {
        this.oauthUsersRepository = oauthUsersRepository;
    }

    // Refresh Token 삭제 (로그아웃)
    @Transactional
    public void deleteRefreshToken(String userId, String refreshToken) {
        // UsersService와 달리, findByUserId의 반환 타입이 Optional<OAuthUsers>임
        // Optional에서 바로 객체를 꺼내야 함
        OAuthUsers oauthuser = oauthUsersRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다: " + userId));

        if (oauthuser.getRefreshToken() != null && oauthuser.getRefreshToken().equals(refreshToken)) {
            oauthuser.setRefreshToken(null);
            oauthuser.setRefreshTokenExpiry(null);
            oauthUsersRepository.save(oauthuser);
        } else {
            throw new RuntimeException("일치하는 Refresh Token을 찾을 수 없거나 이미 만료되었습니다.");
        }
    }    
}
