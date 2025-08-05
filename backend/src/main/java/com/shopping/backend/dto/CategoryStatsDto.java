package com.shopping.backend.dto;

/**
 * 카테고리 통계 정보를 담는 DTO
 * MyBatis에서 복잡한 JOIN 쿼리 결과를 매핑하기 위해 사용
 */
public class CategoryStatsDto {
    
    /** 카테고리 ID */
    private Long id;
    
    /** 카테고리 이름 */
    private String name;
    
    /** 카테고리 코드 */
    private String code;
    
    /** 상품 개수 */
    private Integer productCount;
    
    /** 평균 평점 */
    private Double averageRating;
    
    /** 최소 가격 */
    private Integer minPrice;
    
    /** 최대 가격 */
    private Integer maxPrice;

    // ===== 생성자 =====
    
    public CategoryStatsDto() {}
    
    public CategoryStatsDto(Long id, String name, String code, Integer productCount, 
                          Double averageRating, Integer minPrice, Integer maxPrice) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.productCount = productCount;
        this.averageRating = averageRating;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
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

    public Integer getProductCount() {
        return productCount;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    // ===== Setter 메서드들 =====
    
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }
} 