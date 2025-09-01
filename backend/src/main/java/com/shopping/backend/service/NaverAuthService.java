package com.shopping.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shopping.backend.dto.NaverLoginResponse;
import com.shopping.backend.dto.NaverTokenResponse;
import com.shopping.backend.dto.NaverUserInfoResponse;
import com.shopping.backend.entity.Users;
import com.shopping.backend.entity.OAuthUser;
import com.shopping.backend.jwt.JwtTokenizer;
import com.shopping.backend.repository.UsersRepository;
import com.shopping.backend.repository.OAuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

/**
 * 네이버 OAuth 인증 서비스
 * 네이버 로그인 처리, 토큰 발급, 사용자 정보 조회 등을 담당
 */
@Service
@Transactional
public class NaverAuthService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final JwtTokenizer jwtTokenizer;
    private final UsersRepository usersRepository;
    private final OAuthUserRepository oauthUserRepository;

    // 네이버 OAuth 설정값들 (application.yml에서 주입받음)
    @Value("${naver.oauth.client-id}")
    private String clientId;
    
    @Value("${naver.oauth.client-secret}")
    private String clientSecret;
    
    @Value("${naver.oauth.redirect-uri}")
    private String redirectUri;
    
    @Value("${naver.oauth.authorization-url}")
    private String authorizationUrl;
    
    @Value("${naver.oauth.token-url}")
    private String tokenUrl;
    
    @Value("${naver.oauth.user-info-url}")
    private String userInfoUrl;

    @Autowired
    public NaverAuthService(RestTemplate restTemplate, ObjectMapper objectMapper, 
                           JwtTokenizer jwtTokenizer, UsersRepository usersRepository,
                           OAuthUserRepository oauthUserRepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.jwtTokenizer = jwtTokenizer;
        this.usersRepository = usersRepository;
        this.oauthUserRepository = oauthUserRepository;
    }

    /**
     * 네이버 로그인 URL 생성
     * 프론트엔드에서 네이버 로그인 페이지로 리다이렉트할 때 사용할 URL을 생성
     * 
     * @return 네이버 로그인 URL
     */
    public String generateNaverLoginUrl() {
        // CSRF 공격 방지를 위한 상태 토큰 생성
        String state = UUID.randomUUID().toString();
        
        // 네이버 로그인 URL 생성
        return UriComponentsBuilder.fromHttpUrl(authorizationUrl)
                .queryParam("response_type", "code")
                .queryParam("client_id", clientId)
                .queryParam("redirect_uri", redirectUri)
                .queryParam("state", state)
                .build()
                .toUriString();
    }

    /**
     * 네이버 로그인 처리
     * 인증 코드를 받아서 토큰을 발급받고 사용자 정보를 조회한 후 JWT 토큰을 생성
     * 
     * @param code 네이버 OAuth 인증 코드
     * @return JWT 토큰과 사용자 정보를 포함한 응답
     * @throws Exception 네이버 API 호출 실패 시 예외 발생
     */
    public NaverLoginResponse processNaverLogin(String code) throws Exception {
        System.out.println("네이버 로그인 처리 시작 - code: " + code);
        
        // 1단계: 인증 코드로 액세스 토큰 발급
        NaverTokenResponse tokenResponse = getAccessToken(code);
        
        if (tokenResponse.getError() != null) {
            throw new RuntimeException("네이버 토큰 발급 실패: " + tokenResponse.getErrorDescription());
        }
        
        // 2단계: 액세스 토큰으로 사용자 정보 조회
        NaverUserInfoResponse userInfoResponse = getUserInfo(tokenResponse.getAccessToken());
        
        if (!"00".equals(userInfoResponse.getResultCode())) {
            throw new RuntimeException("네이버 사용자 정보 조회 실패: " + userInfoResponse.getMessage());
        }
        
        // 3단계: 사용자 정보로 JWT 토큰 생성 및 사용자 저장/업데이트
        NaverLoginResponse loginResponse = createJwtTokenAndSaveUser(userInfoResponse.getUserInfo());
        
        System.out.println("네이버 로그인 처리 완료 - userId: " + loginResponse.getUserId());
        
        return loginResponse;
    }

    /**
     * 네이버 OAuth 인증 코드로 액세스 토큰 발급
     * 
     * @param code 네이버 OAuth 인증 코드
     * @return 네이버 토큰 응답 객체
     * @throws Exception API 호출 실패 시 예외 발생
     */
    private NaverTokenResponse getAccessToken(String code) throws Exception {
        System.out.println("네이버 액세스 토큰 발급 요청");
        
        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        
        // 요청 파라미터 설정
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("redirect_uri", redirectUri);
        
        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        // 네이버 토큰 API 호출
        ResponseEntity<NaverTokenResponse> response = restTemplate.postForEntity(
                tokenUrl, request, NaverTokenResponse.class);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("네이버 토큰 API 호출 실패: " + response.getStatusCode());
        }
        
        NaverTokenResponse tokenResponse = response.getBody();
        System.out.println("네이버 액세스 토큰 발급 성공");
        
        return tokenResponse;
    }

    /**
     * 네이버 액세스 토큰으로 사용자 정보 조회
     * 
     * @param accessToken 네이버 액세스 토큰
     * @return 네이버 사용자 정보 응답 객체
     * @throws Exception API 호출 실패 시 예외 발생
     */
    private NaverUserInfoResponse getUserInfo(String accessToken) throws Exception {
        System.out.println("네이버 사용자 정보 조회 요청");
        
        // HTTP 헤더 설정 (Authorization 헤더에 액세스 토큰 포함)
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        
        // HTTP 요청 엔티티 생성
        HttpEntity<String> request = new HttpEntity<>(headers);
        
        // 네이버 사용자 정보 API 호출
        ResponseEntity<NaverUserInfoResponse> response = restTemplate.exchange(
                userInfoUrl, HttpMethod.GET, request, NaverUserInfoResponse.class);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("네이버 사용자 정보 API 호출 실패: " + response.getStatusCode());
        }
        
        NaverUserInfoResponse userInfoResponse = response.getBody();
        System.out.println("네이버 사용자 정보 조회 성공 - userId: " + userInfoResponse.getUserInfo().getId());
        
        return userInfoResponse;
    }

    /**
     * 사용자 정보로 JWT 토큰 생성 및 사용자 저장/업데이트
     * 
     * @param naverUserInfo 네이버 사용자 정보
     * @return JWT 토큰과 사용자 정보를 포함한 로그인 응답
     */
    private NaverLoginResponse createJwtTokenAndSaveUser(NaverUserInfoResponse.NaverUserInfo naverUserInfo) {
        System.out.println("JWT 토큰 생성 및 사용자 저장 시작");
        
        // 네이버 사용자 ID로 기존 OAuth 사용자 조회
        Optional<OAuthUser> existingOAuthUser = oauthUserRepository.findByProviderAndProviderUserId("NAVER", naverUserInfo.getId());
        
        Users user;
        OAuthUser oauthUser;
        
        if (existingOAuthUser.isPresent()) {
            // 기존 OAuth 사용자 정보 업데이트
            oauthUser = existingOAuthUser.get();
            user = usersRepository.findById(oauthUser.getUserId()).orElseThrow(() -> 
                new RuntimeException("연관된 사용자 정보를 찾을 수 없습니다."));
            
            // OAuth 사용자 정보 업데이트
            oauthUser.setProviderName(naverUserInfo.getName());
            oauthUser.setProviderEmail(naverUserInfo.getEmail());
            oauthUser.setProviderNickname(naverUserInfo.getNickname());
            oauthUser.setProviderProfileImage(naverUserInfo.getProfileImage());
            oauthUser.setProviderGender(naverUserInfo.getGender());
            oauthUser.setProviderBirthYear(naverUserInfo.getBirthYear());
            oauthUser.setProviderBirthday(naverUserInfo.getBirthday());
            oauthUser.setProviderAgeRange(naverUserInfo.getAgeRange());
            oauthUser.setProviderMobile(naverUserInfo.getMobile());
            
            System.out.println("기존 OAuth 사용자 정보 업데이트 - userId: " + user.getUserId());
        } else {
            // 새로운 사용자 생성
            user = new Users();
            user.setUserId(generateUserId()); // 고유한 사용자 ID 생성
            user.setUserName(naverUserInfo.getName());
            user.setUserEmail(naverUserInfo.getEmail());
            user.setUserType("NAVER"); // 로그인 타입을 네이버로 설정
            user.setUsgYn("Y"); // 사용 여부 활성화
            
            // 사용자 정보 저장
            user = usersRepository.save(user);
            
            // 새로운 OAuth 사용자 생성
            oauthUser = new OAuthUser();
            oauthUser.setUserId(user.getUserId());
            oauthUser.setProvider("NAVER");
            oauthUser.setProviderUserId(naverUserInfo.getId());
            oauthUser.setProviderEmail(naverUserInfo.getEmail());
            oauthUser.setProviderName(naverUserInfo.getName());
            oauthUser.setProviderNickname(naverUserInfo.getNickname());
            oauthUser.setProviderProfileImage(naverUserInfo.getProfileImage());
            oauthUser.setProviderGender(naverUserInfo.getGender());
            oauthUser.setProviderBirthYear(naverUserInfo.getBirthYear());
            oauthUser.setProviderBirthday(naverUserInfo.getBirthday());
            oauthUser.setProviderAgeRange(naverUserInfo.getAgeRange());
            oauthUser.setProviderMobile(naverUserInfo.getMobile());
            
            System.out.println("새로운 OAuth 사용자 생성 - userId: " + user.getUserId());
        }
        
        // OAuth 사용자 정보 저장
        oauthUserRepository.save(oauthUser);
        
        // JWT 토큰 생성
        String accessToken = jwtTokenizer.createAccessToken(user.getUserId(), user.getUserEmail());
        String refreshToken = jwtTokenizer.createRefreshToken(user.getUserId());
        
        // 리프레시 토큰을 데이터베이스에 저장 (기존 JWT 관련 로직 활용)
        // TODO: RefreshToken 엔티티에 저장하는 로직 추가 필요
        
        System.out.println("JWT 토큰 생성 완료");
        
        // 로그인 응답 생성
        return NaverLoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(3600L) // 1시간
                .userId(user.getUserId())
                .email(user.getUserEmail())
                .nickname(user.getUserName())
                .profileImage(oauthUser.getProviderProfileImage())
                .success(true)
                .message("네이버 로그인이 성공했습니다.")
                .build();
    }

    /**
     * 고유한 사용자 ID 생성
     * 
     * @return 생성된 사용자 ID
     */
    private String generateUserId() {
        return "USER_" + System.currentTimeMillis() + "_" + UUID.randomUUID().toString().substring(0, 8);
    }
}
