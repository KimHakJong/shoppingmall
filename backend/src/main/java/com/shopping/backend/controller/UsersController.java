package com.shopping.backend.controller;

import com.shopping.backend.constant.ErrorCode;
import com.shopping.backend.dto.ApiResponse;
import com.shopping.backend.dto.LoginRequest;
import com.shopping.backend.dto.LoginResponse;
import com.shopping.backend.dto.LogoutRequest;
import com.shopping.backend.entity.Users;
import com.shopping.backend.security.JwtTokenizer;
import com.shopping.backend.service.OAuthUsersService;
import com.shopping.backend.service.UsersService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;
    private final OAuthUsersService oauthUsersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    public UsersController(UsersService usersService, OAuthUsersService oauthUsersService, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.usersService = usersService;
        this.oauthUsersService = oauthUsersService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/join")
    public ResponseEntity<ApiResponse<String>> joinUser(@RequestBody Users user) {
        System.out.println("=== 회원가입 컨트롤러 진입 ===");
        System.out.println("받은 데이터: " + user.getUserId() + ", " + user.getUserName() + ", " + user.getUserEmail());
        
        try {
            // 필수 필드 검증
            if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
                ApiResponse<String> response = ApiResponse.error(
                    "아이디는 필수입니다.",
                    ErrorCode.INVALID_USER_ID,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
                ApiResponse<String> response = ApiResponse.error(
                    "이름은 필수입니다.",
                    ErrorCode.INVALID_USER_NAME,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }
            if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
                ApiResponse<String> response = ApiResponse.error(
                    "비밀번호는 필수입니다.",
                    ErrorCode.INVALID_PASSWORD,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }
            
            usersService.insertUser(user);
            System.out.println("회원가입 성공!");
            
            ApiResponse<String> response = ApiResponse.success(
                "회원가입이 정상적으로 완료되었습니다.",
                "회원가입 성공",
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("회원가입 실패: " + e.getMessage());
            e.printStackTrace();
            
            ApiResponse<String> response = ApiResponse.error(
                "회원가입에 실패하였습니다: " + e.getMessage(),
                ErrorCode.JOIN_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> loginUser(@RequestBody LoginRequest loginRequest) {
        System.out.println("로그인진입");
        Users user;
        try {
            user = usersService.findByUserId(loginRequest.getUserId());
        } catch (RuntimeException e) {
            ApiResponse<LoginResponse> response = ApiResponse.error(
                "사용자 정보가 없습니다.",
                ErrorCode.USER_NOT_FOUND,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }

        if(!passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserPassword())){
            ApiResponse<LoginResponse> response = ApiResponse.error(
                "비밀번호가 일치하지 않습니다.",
                ErrorCode.INVALID_PASSWORD,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(user.getUserId(), user.getUserRole());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getUserId());

        // Refresh Token을 DB에 저장 (7일 후 만료)
        try {
            LocalDateTime refreshTokenExpiry = LocalDateTime.now().plusDays(7);
            usersService.saveRefreshToken(user.getUserId(), refreshToken, refreshTokenExpiry);
        } catch (Exception e) {
            ApiResponse<LoginResponse> response = ApiResponse.error(
                "리프레시 토큰 저장에 실패했습니다: " + e.getMessage(),
                ErrorCode.TOKEN_SAVE_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }

        // LoginResponse 객체 생성
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getUserId());
        loginResponse.setUserRole(user.getUserRole());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setMessage("로그인이 성공했습니다.");

        ApiResponse<LoginResponse> response = ApiResponse.success(
            "로그인이 성공했습니다.",
            loginResponse,
            HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logoutUser(@RequestBody LogoutRequest logoutRequest) {
        System.out.println("로그아웃진입");
        try {
            Users user = usersService.findByUserId(logoutRequest.getUserId());
            if (user != null) {
                if(user.getUserPassword() != null) {
                    // 해당 user의 refreshToken을 null로 처리
                    usersService.deleteRefreshToken(logoutRequest.getUserId(), logoutRequest.getRefreshToken());
                } else {
                    oauthUsersService.deleteRefreshToken(logoutRequest.getUserId(), logoutRequest.getRefreshToken());
                }
                ApiResponse<String> response = ApiResponse.success(
                    "로그아웃이 완료되었습니다.",
                    "로그아웃 성공",
                    HttpStatus.OK.value()
                );
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<String> response = ApiResponse.error(
                    "해당 사용자를 찾을 수 없습니다.",
                    ErrorCode.USER_NOT_FOUND,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            ApiResponse<String> response = ApiResponse.error(
                "로그아웃에 실패했습니다: " + e.getMessage(),
                ErrorCode.LOGOUT_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<LoginResponse>> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // "Bearer " 제거
            String token = refreshToken.substring(7);
            
            // DB에서 Refresh Token 유효성 검증
            if (!usersService.isRefreshTokenValid(token)) {
                ApiResponse<LoginResponse> response = ApiResponse.error(
                    "유효하지 않은 Refresh Token입니다.",
                    ErrorCode.INVALID_REFRESH_TOKEN,
                    HttpStatus.BAD_REQUEST.value()
                );
                return ResponseEntity.badRequest().body(response);
            }

            // Refresh Token으로 사용자 정보 조회
            Users user = usersService.findByRefreshToken(token);
            
            // 새로운 Access Token 생성
            String newAccessToken = jwtTokenizer.createAccessToken(user.getUserId(), user.getUserRole());
            
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setAccessToken(newAccessToken);
            loginResponse.setMessage("토큰이 갱신되었습니다.");
            
            ApiResponse<LoginResponse> response = ApiResponse.success(
                "토큰이 갱신되었습니다.",
                loginResponse,
                HttpStatus.OK.value()
            );
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<LoginResponse> response = ApiResponse.error(
                "토큰 갱신에 실패했습니다: " + e.getMessage(),
                ErrorCode.TOKEN_REFRESH_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<ApiResponse<String>> getProfile() {
        ApiResponse<String> response = ApiResponse.success(
            "인증된 사용자만 접근 가능한 프로필 페이지입니다.",
            "프로필 조회 성공",
            HttpStatus.OK.value()
        );
        return ResponseEntity.ok(response);
    }
}