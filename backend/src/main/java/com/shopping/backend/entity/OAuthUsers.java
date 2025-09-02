package com.shopping.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * OAuth 사용자 엔티티 클래스
 * 외부 OAuth 계정 및 토큰(리프레시) 정보를 저장
 * DB 테이블: auth_users
 */
@Entity
@Table(name = "auth_users")
public class OAuthUsers {

    /** PK: OAuth 회원 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 연관된 Users 테이블의 user_id (Foreign Key) */
    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    /** OAuth 제공자 (NAVER, KAKAO, GOOGLE 등) */
    @Column(name = "provider", length = 20, nullable = false)
    private String provider;

    /** OAuth 제공자에서 제공하는 고유 ID */
    @Column(name = "provider_user_id", length = 255, nullable = false)
    private String providerUserId;

    /** OAuth 리프레시 토큰 (암호화하여 저장 권장) */
    @Column(name = "refresh_token", length = 4096)
    private String refreshToken;

    /** 리프레시 토큰 만료 일시 */
    @Column(name = "refresh_token_expiry")
    private LocalDateTime refreshTokenExpiry;

    /** 발급 범위 */
    @Column(name = "scope", length = 512)
    private String scope;

    /** 최초 생성 시간 */
    @Column(name = "created_tsp", nullable = false, updatable = false)
    private LocalDateTime createdTsp;

    /** 최종 수정 시간 */
    @Column(name = "updated_tsp", nullable = false)
    private LocalDateTime updatedTsp;

    /** 사용 여부 (Y/N) - 스키마에 없지만 운영을 위해 유지하고 싶다면 컬럼 추가 필요 */
    @Transient
    private String isActive;

    // ===== 생성자 =====
    public OAuthUsers() {
    }

    // ===== JPA 생명주기 메서드 =====
    
    @PrePersist
    protected void onCreate() {
        this.createdTsp = LocalDateTime.now();
        this.updatedTsp = LocalDateTime.now();
        System.out.println("=== OAuthUser 엔티티 생성: " + this.providerUserId + " ===");
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedTsp = LocalDateTime.now();
        System.out.println("=== OAuthUser 엔티티 업데이트: " + this.providerUserId + " ===");
    }

    // ===== Getter/Setter =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public LocalDateTime getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(LocalDateTime refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public LocalDateTime getCreatedTsp() {
        return createdTsp;
    }

    public void setCreatedTsp(LocalDateTime createdTsp) {
        this.createdTsp = createdTsp;
    }

    public LocalDateTime getUpdatedTsp() {
        return updatedTsp;
    }

    public void setUpdatedTsp(LocalDateTime updatedTsp) {
        this.updatedTsp = updatedTsp;
    }
}
