package com.shopping.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

/**
 * 네이버 로그인 응답 DTO
 * 네이버 OAuth 로그인 성공 후 반환되는 JWT 토큰과 사용자 정보
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NaverLoginResponse {
    
    /**
     * JWT 액세스 토큰
     * 사용자 인증을 위한 토큰으로, API 요청 시 Authorization 헤더에 포함
     * 토큰 만료 시간은 보통 1시간 정도로 설정
     */
    private String accessToken;
    
    /**
     * JWT 리프레시 토큰
     * 액세스 토큰이 만료되었을 때 새로운 액세스 토큰을 발급받기 위한 토큰
     * 보통 2주~1개월 정도의 긴 만료 시간을 가짐
     */
    private String refreshToken;
    
    /**
     * 토큰 타입
     * 일반적으로 "Bearer"로 설정되어 Authorization 헤더에서 "Bearer {token}" 형태로 사용
     */
    private String tokenType;
    
    /**
     * 액세스 토큰 만료 시간 (초)
     * 토큰이 유효한 시간을 초 단위로 표시
     */
    private Long expiresIn;
    
    /**
     * 사용자 ID
     * 네이버에서 제공하는 고유한 사용자 식별자
     */
    private String userId;
    
    /**
     * 사용자 이메일
     * 네이버 계정에 등록된 이메일 주소
     */
    private String email;
    
    /**
     * 사용자 닉네임
     * 네이버에서 설정한 사용자 이름 또는 닉네임
     */
    private String nickname;
    
    /**
     * 사용자 프로필 이미지 URL
     * 네이버 프로필에 등록된 이미지의 URL
     */
    private String profileImage;
    
    /**
     * 로그인 성공 여부
     * 로그인 처리가 성공적으로 완료되었는지 나타내는 플래그
     */
    private boolean success;
    
    /**
     * 응답 메시지
     * 로그인 결과에 대한 설명 메시지
     */
    private String message;
}
