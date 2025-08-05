package com.shopping.backend.dto;

/**
 * 가격대별 상품 분포 정보를 담는 DTO
 * MyBatis에서 복잡한 CASE 쿼리 결과를 매핑하기 위해 사용
 */
public class PriceDistributionDto {
    
    /** 가격대 범위 */
    private String priceRange;
    
    /** 상품 개수 */
    private Integer productCount;
    
    /** 평균 가격 */
    private Double averagePrice;

    // ===== 생성자 =====
    
    public PriceDistributionDto() {}
    
    public PriceDistributionDto(String priceRange, Integer productCount, Double averagePrice) {
        this.priceRange = priceRange;
        this.productCount = productCount;
        this.averagePrice = averagePrice;
    }

    // ===== Getter 메서드들 =====
    
    public String getPriceRange() {
        return priceRange;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public Double getAveragePrice() {
        return averagePrice;
    }

    // ===== Setter 메서드들 =====
    
    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public void setAveragePrice(Double averagePrice) {
        this.averagePrice = averagePrice;
    }
} 