package com.shopping.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;  // 기본 생성자

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
public class LogoutRequest {
    String userId;
    String refreshToken;
}
