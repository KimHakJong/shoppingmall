package com.shopping.backend.dto;

/**
 * 상품 검색 조건을 담는 DTO
 * MyBatis 동적 쿼리에서 사용
 */
public class ProductSearchDto {
    
    /** 검색 키워드 */
    private String keyword;
    
    /** 카테고리 ID */
    private Long categoryId;
    
    /** 최소 가격 */
    private Integer minPrice;
    
    /** 최대 가격 */
    private Integer maxPrice;
    
    /** 최소 평점 */
    private Double minRating;
    
    /** 정렬 기준 */
    private String sortBy;
    
    /** 페이지 크기 */
    private Integer limit;
    
    /** 오프셋 */
    private Integer offset;

    // ===== 생성자 =====
    
    public ProductSearchDto() {
        this.limit = 20;  // 기본값
        this.offset = 0;  // 기본값
    }
    
    public ProductSearchDto(String keyword, Long categoryId, Integer minPrice, 
                          Integer maxPrice, Double minRating, String sortBy) {
        this();
        this.keyword = keyword;
        this.categoryId = categoryId;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.minRating = minRating;
        this.sortBy = sortBy;
    }

    // ===== Getter 메서드들 =====
    
    public String getKeyword() {
        return keyword;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Integer getMinPrice() {
        return minPrice;
    }

    public Integer getMaxPrice() {
        return maxPrice;
    }

    public Double getMinRating() {
        return minRating;
    }

    public String getSortBy() {
        return sortBy;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getOffset() {
        return offset;
    }

    // ===== Setter 메서드들 =====
    
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setMinPrice(Integer minPrice) {
        this.minPrice = minPrice;
    }

    public void setMaxPrice(Integer maxPrice) {
        this.maxPrice = maxPrice;
    }

    public void setMinRating(Double minRating) {
        this.minRating = minRating;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
} 