package com.shopping.backend.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class LoginResponse {
    private String userId;
    private String userRole;
    private String accessToken;
    private String refreshToken;
    private String message;
}
