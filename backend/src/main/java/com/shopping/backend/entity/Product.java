package com.shopping.backend.entity;

import javax.persistence.*;

/**
 * 상품 엔티티 클래스
 * 
 * @Entity: JPA가 이 클래스를 데이터베이스 테이블과 매핑
 * @Table: 테이블 이름 지정 (기본값은 클래스명)
 */
@Entity
@Table(name = "products")
public class Product {
    
    /** 상품 고유 ID (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 자동 증가
    private Long id;
    
    /** 상품명 (NULL 불가, 최대 200자) */
    @Column(name = "name", nullable = false, length = 200)
    private String name;
    
    /** 상품 설명 (최대 1000자) */
    @Column(name = "description", length = 1000)
    private String description;
    
    /** 가격 (NULL 불가) */
    @Column(name = "price", nullable = false)
    private Integer price;
    
    /** 재고 수량 (NULL 불가, 기본값 0) */
    @Column(name = "stock", nullable = false)
    private Integer stock = 0;
    
    /** 카테고리 ID (외래키) */
    @Column(name = "category_id")
    private Long categoryId;
    
    /** 이미지 URL (최대 500자) */
    @Column(name = "image_url", length = 500)
    private String imageUrl;
    
    /** 인기 상품 여부 (기본값 false) */
    @Column(name = "is_popular", nullable = false)
    private Boolean isPopular = false;
    
    /** 신상품 여부 (기본값 false) */
    @Column(name = "is_new", nullable = false)
    private Boolean isNew = false;
    
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
    public Product() {
        this.createdAt = java.time.LocalDateTime.now();
        this.updatedAt = java.time.LocalDateTime.now();
    }
    
    /**
     * 상품 생성자
     * 
     * @param name 상품명
     * @param description 상품 설명
     * @param price 가격
     * @param stock 재고 수량
     * @param categoryId 카테고리 ID
     */
    public Product(String name, String description, Integer price, Integer stock, Long categoryId) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.categoryId = categoryId;
    }

    // ===== Getter 메서드들 =====
    
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Boolean getIsPopular() {
        return isPopular;
    }

    public Boolean getIsNew() {
        return isNew;
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

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setPrice(Integer price) {
        this.price = price;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setStock(Integer stock) {
        this.stock = stock;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setIsPopular(Boolean isPopular) {
        this.isPopular = isPopular;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
        this.updatedAt = java.time.LocalDateTime.now();
    }

    public void setCreatedAt(java.time.LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(java.time.LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
} 