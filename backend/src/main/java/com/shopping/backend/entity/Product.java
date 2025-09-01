package com.shopping.backend.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 상품 엔티티 클래스
 * DB 테이블: product
 */
@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_menu_id", columnList = "menu_id"),
        @Index(name = "idx_prod_status", columnList = "prod_status"),
        @Index(name = "idx_created_tsp", columnList = "created_tsp")
})
public class Product {

    /** 상품ID (Primary Key) */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prod_id")
    private Long prodId;

    /** 상품명 */
    @Column(name = "prod_name", nullable = false, length = 200)
    private String prodName;

    /** 상품메뉴 (외래키) */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id", referencedColumnName = "menu_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_menu_id"))
    private Menu menu;

    /** 상품설명 */
    @Column(name = "prod_description", columnDefinition = "text")
    private String prodDescription;

    /** 상품가격 */
    @Column(name = "prod_price", nullable = false, precision = 10, scale = 2)
    private BigDecimal prodPrice = BigDecimal.ZERO;

    /** 판매상태 */
    @Column(name = "prod_status", nullable = false, length = 10)
    private String prodStatus;

    /** 최초생성시간 */
    @Column(name = "created_tsp", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime createdTsp;

    /** 최종변경시간 */
    @Column(name = "updated_tsp", nullable = false, columnDefinition = "timestamp")
    private LocalDateTime updatedTsp;

    // ===== 생성자 =====

    public Product() {
        this.createdTsp = LocalDateTime.now();
        this.updatedTsp = LocalDateTime.now();
    }

    public Product(String prodName, Menu menu, String prodDescription, BigDecimal prodPrice, String prodStatus) {
        this();
        this.prodName = prodName;
        this.menu = menu;
        this.prodDescription = prodDescription;
        this.prodPrice = prodPrice;
        this.prodStatus = prodStatus;
    }

    // ===== Getter 메서드 =====

    public Long getProdId() {
        return prodId;
    }

    public String getProdName() {
        return prodName;
    }

    public Menu getMenu() {
        return menu;
    }

    public String getProdDescription() {
        return prodDescription;
    }

    public BigDecimal getProdPrice() {
        return prodPrice;
    }

    public String getProdStatus() {
        return prodStatus;
    }

    public LocalDateTime getCreatedTsp() {
        return createdTsp;
    }

    public LocalDateTime getUpdatedTsp() {
        return updatedTsp;
    }

    // ===== Setter 메서드 =====

    public void setProdId(Long prodId) {
        this.prodId = prodId;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public void setProdDescription(String prodDescription) {
        this.prodDescription = prodDescription;
    }

    public void setProdPrice(BigDecimal prodPrice) {
        this.prodPrice = prodPrice;
    }

    public void setProdStatus(String prodStatus) {
        this.prodStatus = prodStatus;
    }

    public void setCreatedTsp(LocalDateTime createdTsp) {
        this.createdTsp = createdTsp;
    }

    public void setUpdatedTsp(LocalDateTime updatedTsp) {
        this.updatedTsp = updatedTsp;
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
}