package com.shopping.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 회원 엔티티 클래스
 * DB 테이블: users
 */
@Entity
@Table(name = "users")
public class Users {

    /** 아이디 (Primary Key) */
    @Id
    @Column(name = "user_id", length = 20, nullable = false)
    private String userId;

    /** 이름 */
    @Column(name = "user_name", length = 50, nullable = false)
    private String userName;

    /** 비밀번호 */
    @Column(name = "user_password", length = 255)
    private String userPassword;

    /** 회원상태 */
    @Column(name = "user_status", length = 10, nullable = false)
    private String userStatus;

    /** 이메일 */
    @Column(name = "user_email", length = 100)
    private String userEmail;

    /** 휴대폰번호 */
    @Column(name = "cell_tphn", length = 12)
    private String cellTphn;

    /** 생년월일 (YYYYMMDD) */
    @Column(name = "dtbr", length = 8)
    private String dtbr;

    /** 성별 (M/F) */
    @Column(name = "gender", length = 1)
    private String gender;

    /** 우편번호 */
    @Column(name = "zip", length = 6)
    private String zip;

    /** 기본 주소 */
    @Column(name = "bscs_addr", length = 300)
    private String bscsAddr;

    /** 상세 주소 */
    @Column(name = "dtl_addr", length = 300)
    private String dtlAddr;

    /** 권한 */
    @Column(name = "user_role", length = 10, nullable = false)
    private String userRole;

    /** 최초생성시간 */
    @Column(name = "created_tsp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdTsp;

    /** 최종변경시간 */
    @Column(name = "updated_tsp", nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime updatedTsp;

    /** Refresh Token */
    @Column(name = "refresh_token", length = 1024)
    private String refreshToken;

    /** Refresh Token 만료시간 */
    @Column(name = "refresh_token_expiry")
    private LocalDateTime refreshTokenExpiry;

    // ===== 생성자 =====

    public Users() {
    }

    // ===== JPA 생명주기 메서드 =====

    @PrePersist
    protected void onCreate() {
        this.createdTsp = LocalDateTime.now();
        this.updatedTsp = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedTsp = LocalDateTime.now();
    }

    // ===== Getter/Setter =====

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getCellTphn() {
        return cellTphn;
    }

    public void setCellTphn(String cellTphn) {
        this.cellTphn = cellTphn;
    }

    public String getDtbr() {
        return dtbr;
    }

    public void setDtbr(String dtbr) {
        this.dtbr = dtbr;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getBscsAddr() {
        return bscsAddr;
    }

    public void setBscsAddr(String bscsAddr) {
        this.bscsAddr = bscsAddr;
    }

    public String getDtlAddr() {
        return dtlAddr;
    }

    public void setDtlAddr(String dtlAddr) {
        this.dtlAddr = dtlAddr;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
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
}
