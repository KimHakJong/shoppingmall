package com.shopping.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 네이버 로그인 요청 DTO
 * 네이버 OAuth 인증 코드를 받아서 처리하기 위한 요청 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NaverLoginRequest {
    
    /**
     * 네이버 OAuth 인증 코드
     * 네이버 로그인 페이지에서 사용자가 인증 후 받은 임시 코드
     * 이 코드를 사용하여 네이버 API에서 액세스 토큰을 발급받음
     */
    private String code;
    
    /**
     * 상태 값 (선택사항)
     * CSRF 공격 방지를 위한 상태 토큰
     * 네이버 로그인 요청 시 전송한 state 값과 일치하는지 확인
     */
    private String state;
}
