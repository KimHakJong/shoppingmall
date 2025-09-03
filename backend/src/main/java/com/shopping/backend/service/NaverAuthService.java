package com.shopping.backend.service;

import com.shopping.backend.dto.NaverLoginResponse;
import com.shopping.backend.dto.NaverTokenResponse;
import com.shopping.backend.dto.NaverUserInfoResponse;
import com.shopping.backend.entity.Users;
import com.shopping.backend.entity.OAuthUsers;
import com.shopping.backend.repository.UsersRepository;
import com.shopping.backend.security.TokenEncryptor;
import com.shopping.backend.repository.OAuthUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 네이버 OAuth 인증 서비스
 * 네이버 로그인 처리, 토큰 발급, 사용자 정보 조회 등을 담당
 */
@Service
@Transactional
public class NaverAuthService {

    private final RestTemplate restTemplate;
    private final UsersRepository usersRepository;
    private final OAuthUsersRepository oauthUsersRepository;

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
    public NaverAuthService(RestTemplate restTemplate, 
                           UsersRepository usersRepository, OAuthUsersRepository oauthUsersRepository) {
        this.restTemplate = restTemplate;
        this.usersRepository = usersRepository;
        this.oauthUsersRepository = oauthUsersRepository;
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
        
        // 3단계: 사용자 정보로 JWT 토큰 생성 및 사용자 저장/업데이트 --> JWT토큰은 자체로그인할때만, Oauth refresh 토큰만 따로 저장필요(oauth db에)
        NaverLoginResponse loginResponse = SaveAuthUser(userInfoResponse.getUserInfo(), tokenResponse);
        
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
        params.add("client_id", "dVpO1p7RfCQsK9c5FNIh");
        params.add("client_secret", "30g56ObElc");
        params.add("code", code);
        params.add("redirect_uri", "http://localhost:3000/naverlogincollback");
        
        // HTTP 요청 엔티티 생성
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        
        // 네이버 토큰 API 호출
        ResponseEntity<NaverTokenResponse> response = restTemplate.postForEntity(
                tokenUrl, request, NaverTokenResponse.class);
        
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new RuntimeException("네이버 토큰 API 호출 실패: " + response.getStatusCode());
        }
        
        NaverTokenResponse tokenResponse = response.getBody();
        System.out.println("tokenResponse :" + tokenResponse.toString());
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
        // toString()이 정상적으로 동작하도록 하기 위해 lombok의 @ToString 어노테이션을 NaverUserInfoResponse와 NaverUserInfo 클래스에 추가해야 합니다.
        // 예시: @ToString
        // 실제 적용 후 아래 출력문이 객체의 모든 필드를 보기 좋게 출력합니다.
        System.out.println("네이버 사용자 정보 조회 성공 - userInfoResponse: " + userInfoResponse);
        return userInfoResponse;
    }

    /**
     * 사용자 정보로 JWT 토큰 생성 및 사용자 저장/업데이트
     * 
     * @param naverUserInfo 네이버 사용자 정보
     * @return 토큰과 사용자 정보를 포함한 로그인 응답
     * @throws Exception 
     */
    private NaverLoginResponse SaveAuthUser(NaverUserInfoResponse.NaverUserInfo naverUserInfo, NaverTokenResponse tokenResponse) throws Exception {
        System.out.println("사용자 저장 시작");
        
        // 네이버 사용자 ID로 기존 OAuth 사용자 조회
        Optional<OAuthUsers> existingOAuthUser = oauthUsersRepository.findByProviderAndProviderUserId("NAVER", naverUserInfo.getId());
        
        Users user;
        OAuthUsers oauthUser;
        
        if (existingOAuthUser.isPresent()) {
            // 기존 사용자 정보 찾기
            oauthUser = existingOAuthUser.get();
            user = usersRepository.findById(oauthUser.getUserId()).orElseThrow(() -> 
                new RuntimeException("연관된 사용자 정보를 찾을 수 없습니다."));
            
            //yyyy + MM-DD => yyyyMMDD 형식 변환
            String DtbrFormat = naverUserInfo.getBirthYear().substring(2,3) + naverUserInfo.getBirthday().replace("-", "");

            // 기존 Users 사용자 정보 설정
            user.setUserName(naverUserInfo.getName());
            user.setUserEmail(naverUserInfo.getEmail());
            user.setDtbr(DtbrFormat);
            user.setCellTphn(naverUserInfo.getMobile().replaceAll("-", ""));

            // 기존 OAuth 사용자 정보 설정
            String encryptedExisting = TokenEncryptor.encrypt(tokenResponse.getRefreshToken());
            oauthUser.setRefreshToken(encryptedExisting); // OAuth 리프레시 토큰 암호화 저장
            oauthUser.setRefreshTokenExpiry(LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn())); // 리프레시 토큰 만료 시간 (네이버는 액세스 토큰 만료 시간으로 제공하는 경우가 많으므로 적절히 조정 필요)
            oauthUser.setScope(tokenResponse.getScope());
            
            System.out.println("기존 OAuth 사용자 정보 업데이트 - userId: " + user.getUserId());
        } else {

            //yyyy + MM-DD => yyyyMMDD 형식 변환
             String DtbrFormat = naverUserInfo.getBirthYear().substring(2,3) + naverUserInfo.getBirthday().replace("-", "");
          
            // 새로운 Users 사용자 정보 설정
            user = new Users();
            user.setUserId(generateUserId()); // 고유한 사용자 ID 생성
            user.setUserName(naverUserInfo.getName());
            user.setUserStatus("0");            
            user.setUserEmail(naverUserInfo.getEmail());
            user.setDtbr(DtbrFormat);
            user.setCellTphn(naverUserInfo.getMobile().replaceAll("-", ""));
            user.setUserRole("USER");
            
            
            // 새로운 OAuth 사용자 생성
            oauthUser = new OAuthUsers();
            oauthUser.setUserId(user.getUserId());
            oauthUser.setProvider("NAVER");
            oauthUser.setProviderUserId(naverUserInfo.getId());
            System.out.println("naverUserInfo.IdL"+naverUserInfo.getId());
            String encrypted = TokenEncryptor.encrypt(tokenResponse.getRefreshToken());

            oauthUser.setRefreshToken(encrypted); // OAuth 리프레시 토큰 저장
            oauthUser.setRefreshTokenExpiry(LocalDateTime.now().plusSeconds(tokenResponse.getExpiresIn())); // 리프레시 토큰 만료 시간
            oauthUser.setScope(tokenResponse.getScope());
            
            System.out.println("새로운 OAuth 사용자 생성 - userId: " + user.getUserId());
        }
        try {
        // 사용자 정보 저장
        user = usersRepository.save(user);        

        // OAuth 사용자 정보 저장
        oauthUser = oauthUsersRepository.save(oauthUser); 
        } catch (Exception e) {
            throw new RuntimeException("사용자 저장 에러:"+e.getMessage());
        }

        System.out.println("사용자 저장 완료");
        
        // 로그인 응답 생성
        return NaverLoginResponse.builder()
                .accessToken(tokenResponse.getAccessToken())
                .refreshToken(oauthUser.getRefreshToken())
                .userId(user.getUserId())
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
        String ts = Long.toString(System.currentTimeMillis(), 36);
        int r = ThreadLocalRandom.current().nextInt(0, 36 * 36 * 36 * 36);
        String rand = String.format("%4s", Integer.toString(r, 36)).replace(' ', '0');
        return "N_" + ts + rand;
    }
}