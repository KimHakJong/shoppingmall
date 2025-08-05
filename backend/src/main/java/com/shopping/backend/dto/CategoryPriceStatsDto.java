package com.shopping.backend.dto;

/**
 * 카테고리별 가격 통계 정보를 담는 DTO
 * MyBatis에서 복잡한 JOIN 쿼리 결과를 매핑하기 위해 사용
 */
public class CategoryPriceStatsDto {
    
    /** 카테고리 이름 */
    private String categoryName;
    
    /** 총 상품 개수 */
    private Integer totalProducts;
    
    /** 평균 가격 */
    private Double averagePrice;
    
    /** 총 가치 */
    private Double totalValue;
    
    /** 최소 가격 */
    private Integer minPrice;
    
    /** 최대 가격 */
    private Integer maxPrice;

    // ===== 생성자 =====
    
    public CategoryPriceStatsDto() {}
    
    public CategoryPriceStatsDto(String categoryName, Integer totalProducts, 
                               Double averagePrice, Double totalValue, 
                               Integer minPrice, Integer maxPrice) {
        this.categoryName = categoryName;
        this.totalProducts = totalProducts;
        this.averagePrice = averagePrice;
        this.totalValue = totalValue;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
    }

    // ===== Getter 메서드들 =====
    
    public String getCategoryName() {
        return categoryName;
    }

    public Integer getTotalProducts() {
        return totalProducts;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    // ===== Setter 메서드들 =====
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setTotalProducts(Integer totalProducts) {
        this.totalProducts = totalProducts;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
} 