package com.shopping.backend.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 메뉴 엔티티 클래스
 * DB 테이블: menus
 */
@Entity
@Table(name = "menus")
public class Menu {

    /** 메뉴ID (Primary Key) */
    @Id
    @Column(name = "menu_id", length = 10, nullable = false)
    private String menuId;

    /** 메뉴명 */
    @Column(name = "menu_name", length = 100, nullable = false)
    private String menuName;

    /** 상위메뉴ID */
    @Column(name = "parent_menu_id", length = 10)
    private String parentMenuId;

    /** 메뉴깊이 */
    @Column(name = "menu_depth", nullable = false)
    private Integer menuDepth;

    /** 메뉴순서 */
    @Column(name = "menu_order", nullable = false)
    private Integer menuOrder;

    /** 사용여부 */
    @Column(name = "usg_yn", length = 1, nullable = false)
    private String usgYn;

    /** 최초생성자ID */
    @Column(name = "created_id", length = 50, nullable = false)
    private String createdId;

    /** 최초생성시간 */
    @Column(name = "created_tsp", nullable = false)
    private LocalDateTime createdTsp;

    /** 최종변경자ID */
    @Column(name = "updated_id", length = 50, nullable = false)
    private String updatedId;

    /** 최종변경시간 */
    @Column(name = "updated_tsp", nullable = false)
    private LocalDateTime updatedTsp;

    /** 관리자여부 */
    @Column(name = "admin_yn", length = 1)
    private String adminYn;

    /** ID */
    @Column(name = "id", nullable = false)
    private Long id;

    /** 코드 */
    @Column(name = "code", length = 50, nullable = false)
    private String code;

    /** 생성시간 */
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    /** 이름 */
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    /** 수정시간 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ===== 생성자 =====

    public Menu() {
    }

    // ===== JPA 생명주기 메서드 =====
    
    @PrePersist
    protected void onCreate() {
        this.createdTsp = LocalDateTime.now();
        this.updatedTsp = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        System.out.println("=== Menu 엔티티 생성: " + this.menuId + " ===");
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedTsp = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        System.out.println("=== Menu 엔티티 업데이트: " + this.menuId + " ===");
    }

    // ===== Getter/Setter =====

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getParentMenuId() {
        return parentMenuId;
    }

    public void setParentMenuId(String parentMenuId) {
        this.parentMenuId = parentMenuId;
    }

    public Integer getMenuDepth() {
        return menuDepth;
    }

    public void setMenuDepth(Integer menuDepth) {
        this.menuDepth = menuDepth;
    }

    public Integer getMenuOrder() {
        return menuOrder;
    }

    public void setMenuOrder(Integer menuOrder) {
        this.menuOrder = menuOrder;
    }

    public String getUsgYn() {
        return usgYn;
    }

    public void setUsgYn(String usgYn) {
        this.usgYn = usgYn;
    }

    public String getCreatedId() {
        return createdId;
    }

    public void setCreatedId(String createdId) {
        this.createdId = createdId;
    }

    public LocalDateTime getCreatedTsp() {
        return createdTsp;
    }

    public void setCreatedTsp(LocalDateTime createdTsp) {
        this.createdTsp = createdTsp;
    }

    public String getUpdatedId() {
        return updatedId;
    }

    public void setUpdatedId(String updatedId) {
        this.updatedId = updatedId;
    }

    public LocalDateTime getUpdatedTsp() {
        return updatedTsp;
    }

    public void setUpdatedTsp(LocalDateTime updatedTsp) {
        this.updatedTsp = updatedTsp;
    }

    public String getAdminYn() {
        return adminYn;
    }

    public void setAdminYn(String adminYn) {
        this.adminYn = adminYn;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "menuId='" + menuId + '\'' +
                ", menuName='" + menuName + '\'' +
                ", parentMenuId='" + parentMenuId + '\'' +
                ", menuDepth=" + menuDepth +
                ", menuOrder=" + menuOrder +
                ", usgYn='" + usgYn + '\'' +
                ", createdId='" + createdId + '\'' +
                ", createdTsp=" + createdTsp +
                ", updatedId='" + updatedId + '\'' +
                ", updatedTsp=" + updatedTsp +
                ", adminYn='" + adminYn + '\'' +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", createdAt=" + createdAt +
                ", name='" + name + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
