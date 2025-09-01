package com.shopping.backend.controller;

import com.shopping.backend.constant.ErrorCode;
import com.shopping.backend.dto.ApiResponse;
import com.shopping.backend.dto.NaverLoginRequest;
import com.shopping.backend.dto.NaverLoginResponse;
import com.shopping.backend.service.NaverAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 네이버 OAuth 인증 컨트롤러
 * 네이버 로그인 관련 API 엔드포인트를 제공
 */
@RestController
@RequestMapping("/api/auth/naver")
public class NaverAuthController {

    private final NaverAuthService naverAuthService;

    @Autowired
    public NaverAuthController(NaverAuthService naverAuthService) {
        this.naverAuthService = naverAuthService;
    }

    /**
     * 네이버 로그인 처리
     * 네이버에서 받은 인증 코드를 사용하여 사용자 정보를 조회하고 JWT 토큰을 발급
     * 
     * @param request 네이버 인증 코드를 포함하는 요청 DTO
     * @return JWT 토큰과 사용자 정보를 포함한 응답
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<NaverLoginResponse>> naverLogin(@RequestBody NaverLoginRequest request) {
        try {
            System.out.println("네이버 로그인 요청 - code: " + request.getCode());
            
            // 인증 코드 유효성 검사
            if (request.getCode() == null || request.getCode().trim().isEmpty()) {
                ApiResponse<NaverLoginResponse> response = ApiResponse.error(
                    "네이버 인증 코드는 필수입니다.",
                    ErrorCode.INVALID_REQUEST,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }

            // 네이버 인증 서비스를 통해 로그인 처리
            NaverLoginResponse loginResponse = naverAuthService.processNaverLogin(request.getCode());
            
            ApiResponse<NaverLoginResponse> response = ApiResponse.success(
                "네이버 로그인이 성공했습니다.",
                loginResponse,
                HttpStatus.OK.value()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("네이버 로그인 오류: " + e.getMessage());
            
            ApiResponse<NaverLoginResponse> response = ApiResponse.error(
                "네이버 로그인에 실패했습니다: " + e.getMessage(),
                ErrorCode.EXTERNAL_API_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 네이버 로그인 URL 생성
     * 프론트엔드에서 네이버 로그인 페이지로 리다이렉트할 때 사용할 URL을 제공
     * 
     * @return 네이버 로그인 URL
     */
    @GetMapping("/login-url")
    public ResponseEntity<ApiResponse<String>> getNaverLoginUrl() {
        try {
            String loginUrl = naverAuthService.generateNaverLoginUrl();
            
            ApiResponse<String> response = ApiResponse.success(
                "네이버 로그인 URL을 성공적으로 생성했습니다.",
                loginUrl,
                HttpStatus.OK.value()
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            System.out.println("네이버 로그인 URL 생성 오류: " + e.getMessage());
            
            ApiResponse<String> response = ApiResponse.error(
                "네이버 로그인 URL 생성에 실패했습니다: " + e.getMessage(),
                ErrorCode.EXTERNAL_API_ERROR,
                HttpStatus.INTERNAL_SERVER_ERROR.value()
            );
            
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
