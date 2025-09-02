package com.shopping.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * 네이버 토큰 API 응답 DTO
 * 네이버 OAuth 토큰 발급 API의 응답을 매핑하는 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NaverTokenResponse {
    
    /**
     * 액세스 토큰
     * 네이버 API에 접근할 수 있는 권한을 부여하는 토큰
     */
    @JsonProperty("access_token")
    private String accessToken;
    
    /**
     * 리프레시 토큰
     * 액세스 토큰이 만료되었을 때 새로운 액세스 토큰을 발급받기 위한 토큰
     */
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    /**
     * 토큰 타입
     * 일반적으로 "bearer"로 설정됨
     */
    @JsonProperty("token_type")
    private String tokenType;
    
    /**
     * 액세스 토큰 만료 시간 (초)
     */
    @JsonProperty("expires_in")
    private Integer expiresIn;
    
    /**
     * 에러 코드 (에러 발생 시)
     */
    @JsonProperty("error")
    private String error;
    
    /**
     * 에러 설명 (에러 발생 시)
     */
    @JsonProperty("error_description")
    private String errorDescription;

    /**
     * 발급된 액세스 토큰의 접근 범위
     */
    @JsonProperty("scope")
    private String scope;
}
