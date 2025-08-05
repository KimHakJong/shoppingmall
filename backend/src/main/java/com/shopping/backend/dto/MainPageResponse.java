package com.shopping.backend.dto;

import java.util.List;

/**
 * 쇼핑몰 메인 페이지 응답을 위한 DTO(Data Transfer Object) 클래스
 * 
 * DTO란? Data Transfer Object
 * - 클라이언트(프론트엔드)와 서버 간에 데이터를 주고받기 위한 객체
 * - 데이터베이스 엔티티와 분리하여 API 응답 형태를 정의
 * 
 * 이 클래스는 메인 페이지에 필요한 모든 데이터를 포함:
 * - 카테고리 목록
 * - 인기 상품 목록
 * - 신상품 목록
 */
public class MainPageResponse {
    
    /** 카테고리 목록 */
    private List<CategoryDto> categories;
    
    /** 인기 상품 목록 */
    private List<ProductDto> popularProducts;
    
    /** 신상품 목록 */
    private List<ProductDto> newProducts;

    /**
     * 메인 페이지 응답 객체를 생성하는 생성자
     * 
     * @param categories 카테고리 목록
     * @param popularProducts 인기 상품 목록
     * @param newProducts 신상품 목록
     */
    public MainPageResponse(List<CategoryDto> categories, List<ProductDto> popularProducts, List<ProductDto> newProducts) {
        this.categories = categories;
        this.popularProducts = popularProducts;
        this.newProducts = newProducts;
    }

    // ===== Getter 메서드들 =====
    // JSON 직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 카테고리 목록을 반환
     * @return 카테고리 목록
     */
    public List<CategoryDto> getCategories() {
        return categories;
    }

    /**
     * 인기 상품 목록을 반환
     * @return 인기 상품 목록
     */
    public List<ProductDto> getPopularProducts() {
        return popularProducts;
    }

    /**
     * 신상품 목록을 반환
     * @return 신상품 목록
     */
    public List<ProductDto> getNewProducts() {
        return newProducts;
    }

    // ===== Setter 메서드들 =====
    // JSON 역직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 카테고리 목록을 설정
     * @param categories 카테고리 목록
     */
    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    /**
     * 인기 상품 목록을 설정
     * @param popularProducts 인기 상품 목록
     */
    public void setPopularProducts(List<ProductDto> popularProducts) {
        this.popularProducts = popularProducts;
    }

    /**
     * 신상품 목록을 설정
     * @param newProducts 신상품 목록
     */
    public void setNewProducts(List<ProductDto> newProducts) {
        this.newProducts = newProducts;
    }
} 