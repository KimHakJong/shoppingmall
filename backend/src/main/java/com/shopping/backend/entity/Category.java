package com.shopping.backend.entity;

import javax.persistence.*;

/**
 * 카테고리 엔티티 클래스
 * 
 * @Entity: JPA가 이 클래스를 데이터베이스 테이블과 매핑
 * @Table: 테이블 이름 지정 (기본값은 클래스명)
 */
@Entity
@Table(name = "categories")
public class Category {
    
    /** 카테고리 고유 ID (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가
    private Long id;
    
    /** 카테고리 이름 (NULL 불가, 최대 100자) */
    @Column(name = "name", nullable = false, length = 100)
    private String name;
    
    /** 카테고리 코드 (NULL 불가, 최대 50자, 유니크) */
    @Column(name = "code", nullable = false, length = 50, unique = true)
    private String code;
    
    /** 생성일시 */
    @Column(name = "created_at")
    private java.time.LocalDateTime createdAt;
    
    /** 수정일시 */
    @Column(name = "updated_at")
    private java.time.LocalDateTime updatedAt;

    // ===== 생성자 =====
    
    /**
     * 기본 생성자 (JPA에서 필요)
     */
    public Category() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * 카테고리 생성자
     * 
     * @param name 카테고리 이름
     * @param code 카테고리 코드
     */
    public Category(String name, String code) {
        this();
        this.name = name;
        this.code = code;
    }

    // ===== Getter 메서드들 =====
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public java.time.LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public java.time.LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ===== Setter 메서드들 =====
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setCode(String code) {
        this.code = code;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 