package com.shopping.backend.dto;

/**
 * 카테고리별 상품 통계 정보를 담는 DTO
 * MyBatis에서 복잡한 JOIN 쿼리 결과를 매핑하기 위해 사용
 */
public class CategoryProductStatsDto {
    
    /** 카테고리 이름 */
    private String categoryName;
    
    /** 상품 개수 */
    private Integer productCount;
    
    /** 평균 가격 */
    private Double averagePrice;
    
    /** 평균 평점 */
    private Double averageRating;
    
    /** 총 가치 */
    private Double totalValue;

    // ===== 생성자 =====
    
    public CategoryProductStatsDto() {}
    
    public CategoryProductStatsDto(String categoryName, Integer productCount, 
                                 Double averagePrice, Double averageRating, 
                                 Double totalValue) {
        this.categoryName = categoryName;
        this.productCount = productCount;
        this.averagePrice = averagePrice;
        this.averageRating = averageRating;
        this.totalValue = totalValue;
    }

    // ===== Getter 메서드들 =====
    
    public String getCategoryName() {
        return categoryName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Double getTotalValue() {
        return totalValue;
    }

    // ===== Setter 메서드들 =====
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setTotalValue(Double totalValue) {
        this.totalValue = totalValue;
    }
} 