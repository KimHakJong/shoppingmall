package com.shopping.backend.dto;

/**
 * 상품 통계 정보를 담는 DTO
 * MyBatis에서 복잡한 쿼리 결과를 매핑하기 위해 사용
 */
public class ProductStatsDto {
    
    /** 총 상품 개수 */
    private Integer totalProducts;
    
    /** 평균 가격 */
    private Double averagePrice;
    
    /** 최소 가격 */
    private Integer minPrice;
    
    /** 최대 가격 */
    private Integer maxPrice;
    
    /** 평균 평점 */
    private Double averageRating;
    
    /** 고평점 상품 개수 (4.0 이상) */
    private Integer highRatedProducts;

    // ===== 생성자 =====
    
    public ProductStatsDto() {}
    
    public ProductStatsDto(Integer totalProducts, Double averagePrice, 
                         Integer minPrice, Integer maxPrice, 
                         Double averageRating, Integer highRatedProducts) {
        this.totalProducts = totalProducts;
        this.averagePrice = averagePrice;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.averageRating = averageRating;
        this.highRatedProducts = highRatedProducts;
    }

    // ===== Getter 메서드들 =====
    
    public Integer getTotalProducts() {
        return totalProducts;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getHighRatedProducts() {
        return highRatedProducts;
    }

    // ===== Setter 메서드들 =====
    
    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setHighRatedProducts(Integer highRatedProducts) {
        this.highRatedProducts = highRatedProducts;
    }
} 