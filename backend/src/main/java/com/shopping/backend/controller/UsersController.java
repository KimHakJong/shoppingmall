package com.shopping.backend.controller;

import com.shopping.backend.dto.LoginRequest;
import com.shopping.backend.dto.LoginResponse;
import com.shopping.backend.entity.Users;
import com.shopping.backend.service.UsersService;
import com.shopping.backend.jwt.JwtTokenizer;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/users")
public class UsersController {

    private final UsersService usersService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenizer jwtTokenizer;

    public UsersController(UsersService usersService, PasswordEncoder passwordEncoder, JwtTokenizer jwtTokenizer) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenizer = jwtTokenizer;
    }

    @PostMapping("/join")
    public ResponseEntity joinUser(@RequestBody Users user) {
        System.out.println("=== 회원가입 컨트롤러 진입 ===");
        System.out.println("받은 데이터: " + user.getUserId() + ", " + user.getUserName() + ", " + user.getUserEmail());
        
        try {
            // 필수 필드 검증
            if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("아이디는 필수입니다.");
            }
            if (user.getUserName() == null || user.getUserName().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("이름은 필수입니다.");
            }
            if (user.getUserPassword() == null || user.getUserPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("비밀번호는 필수입니다.");
            }
            
            usersService.insertUser(user);
            System.out.println("회원가입 성공!");
            return ResponseEntity.ok("회원가입이 정상적으로 완료되었습니다.");
        } catch (Exception e) {
            System.out.println("회원가입 실패: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("회원가입에 실패하였습니다: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity loginUser (@RequestBody LoginRequest loginRequest) {
        System.out.println("로그인진입");
        Users user;
        try {
            user = usersService.findByUserId(loginRequest.getUserId());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("사용자 정보가 없습니다.(1)");
        }

        if(!passwordEncoder.matches(loginRequest.getUserPassword(), user.getUserPassword())){
            return ResponseEntity.badRequest().body("사용자 정보가 없습니다.(2)");
        }

        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(user.getUserId(), user.getUserRole());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getUserId());

        // Refresh Token을 DB에 저장 (7일 후 만료)
        try {
            LocalDateTime refreshTokenExpiry = LocalDateTime.now().plusDays(7);
            usersService.saveRefreshToken(user.getUserId(), refreshToken, refreshTokenExpiry);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("리프레시 토큰 저장에 실패했습니다: " + e.getMessage());
        }

        // LoginResponse 객체 생성
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(user.getUserId());
        loginResponse.setUserRole(user.getUserRole());
        loginResponse.setAccessToken(accessToken);
        loginResponse.setRefreshToken(refreshToken);
        loginResponse.setMessage("로그인이 성공했습니다.");

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh")
    public ResponseEntity refreshToken(@RequestHeader("Authorization") String refreshToken) {
        try {
            // "Bearer " 제거
            String token = refreshToken.substring(7);
            
            // DB에서 Refresh Token 유효성 검증
            if (!usersService.isRefreshTokenValid(token)) {
                return ResponseEntity.badRequest().body("유효하지 않은 Refresh Token입니다.");
            }

            // Refresh Token으로 사용자 정보 조회
            Users user = usersService.findByRefreshToken(token);
            
            // 새로운 Access Token 생성
            String newAccessToken = jwtTokenizer.createAccessToken(user.getUserId(), user.getUserRole());
            
            LoginResponse response = new LoginResponse();
            response.setAccessToken(newAccessToken);
            response.setMessage("토큰이 갱신되었습니다.");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("토큰 갱신에 실패했습니다: " + e.getMessage());
        }
    }

    @PostMapping("/profile")
    public ResponseEntity getProfile() {
        return ResponseEntity.ok("인증된 사용자만 접근 가능한 프로필 페이지입니다.");
    }

    @PostMapping("/logout")
    public ResponseEntity logout(@RequestHeader("Authorization") String accessToken) {
        try {
            // "Bearer " 제거
            String token = accessToken.substring(7);
            
            // 토큰에서 사용자 ID 추출
            String userId = jwtTokenizer.getUserIdFromToken(token);
            
            // Refresh Token 삭제
            usersService.deleteRefreshToken(userId);
            
            return ResponseEntity.ok("로그아웃이 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("로그아웃에 실패했습니다: " + e.getMessage());
        }
    }
}