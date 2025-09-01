package com.shopping.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * OAuth 사용자 엔티티 클래스
 * 네이버, 카카오, 구글 등 OAuth 로그인 사용자 정보를 저장
 * DB 테이블: oauth_users
 */
@Entity
@Table(name = "oauth_users")
public class OAuthUser {

    /** OAuth 사용자 ID (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_user_id")
    private Long oauthUserId;

    /** 연관된 Users 테이블의 user_id (Foreign Key) */
    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    /** OAuth 제공자 (NAVER, KAKAO, GOOGLE 등) */
    @Column(name = "provider", length = 20, nullable = false)
    private String provider;

    /** OAuth 제공자에서 제공하는 고유 ID */
    @Column(name = "provider_user_id", length = 100, nullable = false)
    private String providerUserId;

    /** OAuth 제공자에서 제공하는 이메일 */
    @Column(name = "provider_email", length = 100)
    private String providerEmail;

    /** OAuth 제공자에서 제공하는 이름 */
    @Column(name = "provider_name", length = 100)
    private String providerName;

    /** OAuth 제공자에서 제공하는 닉네임 */
    @Column(name = "provider_nickname", length = 100)
    private String providerNickname;

    /** OAuth 제공자에서 제공하는 프로필 이미지 URL */
    @Column(name = "provider_profile_image", length = 500)
    private String providerProfileImage;

    /** OAuth 제공자에서 제공하는 성별 */
    @Column(name = "provider_gender", length = 10)
    private String providerGender;

    /** OAuth 제공자에서 제공하는 생년월일 */
    @Column(name = "provider_birth_year", length = 4)
    private String providerBirthYear;

    /** OAuth 제공자에서 제공하는 생일 */
    @Column(name = "provider_birthday", length = 10)
    private String providerBirthday;

    /** OAuth 제공자에서 제공하는 연령대 */
    @Column(name = "provider_age_range", length = 10)
    private String providerAgeRange;

    /** OAuth 제공자에서 제공하는 휴대폰 번호 */
    @Column(name = "provider_mobile", length = 20)
    private String providerMobile;

    /** OAuth 액세스 토큰 (암호화하여 저장) */
    @Column(name = "access_token", length = 1000)
    private String accessToken;

    /** OAuth 리프레시 토큰 (암호화하여 저장) */
    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    /** 액세스 토큰 만료 시간 */
    @Column(name = "access_token_expires_at")
    private LocalDateTime accessTokenExpiresAt;

    /** 최초 생성 시간 */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /** 최종 수정 시간 */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /** 사용 여부 (Y/N) */
    @Column(name = "is_active", length = 1, nullable = false)
    private String isActive;

    // ===== 생성자 =====
    public OAuthUser() {
    }

    // ===== JPA 생명주기 메서드 =====
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.isActive == null) {
            this.isActive = "Y";
        }
        System.out.println("=== OAuthUser 엔티티 생성: " + this.providerUserId + " ===");
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        System.out.println("=== OAuthUser 엔티티 업데이트: " + this.providerUserId + " ===");
    }

    // ===== Getter/Setter =====

    public Long getOauthUserId() {
        return oauthUserId;
    }

    public void setOauthUserId(Long oauthUserId) {
        this.oauthUserId = oauthUserId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    public String getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(String providerUserId) {
        this.providerUserId = providerUserId;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderNickname() {
        return providerNickname;
    }

    public void setProviderNickname(String providerNickname) {
        this.providerNickname = providerNickname;
    }

    public String getProviderProfileImage() {
        return providerProfileImage;
    }

    public void setProviderProfileImage(String providerProfileImage) {
        this.providerProfileImage = providerProfileImage;
    }

    public String getProviderGender() {
        return providerGender;
    }

    public void setProviderGender(String providerGender) {
        this.providerGender = providerGender;
    }

    public String getProviderBirthYear() {
        return providerBirthYear;
    }

    public void setProviderBirthYear(String providerBirthYear) {
        this.providerBirthYear = providerBirthYear;
    }

    public String getProviderBirthday() {
        return providerBirthday;
    }

    public void setProviderBirthday(String providerBirthday) {
        this.providerBirthday = providerBirthday;
    }

    public String getProviderAgeRange() {
        return providerAgeRange;
    }

    public void setProviderAgeRange(String providerAgeRange) {
        this.providerAgeRange = providerAgeRange;
    }

    public String getProviderMobile() {
        return providerMobile;
    }

    public void setProviderMobile(String providerMobile) {
        this.providerMobile = providerMobile;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getAccessTokenExpiresAt() {
        return accessTokenExpiresAt;
    }

    public void setAccessTokenExpiresAt(LocalDateTime accessTokenExpiresAt) {
        this.accessTokenExpiresAt = accessTokenExpiresAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
}
