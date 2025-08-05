package com.shopping.backend.dto;

/**
 * 상품 정보를 담는 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 상품의 모든 정보를 포함:
 * - 상품 ID, 이름, 설명
 * - 가격, 이미지 URL, 평점
 * 
 * JSON 직렬화/역직렬화를 위해 getter/setter 메서드가 필요
 */
public class ProductDto {
    
    /** 상품 고유 ID */
    private Long id;
    
    /** 상품명 */
    private String name;
    
    /** 상품 설명 */
    private String description;
    
    /** 상품 가격 (원 단위) */
    private int price;
    
    /** 상품 이미지 URL */
    private String imageUrl;
    
    /** 상품 평점 (1.0 ~ 5.0) */
    private double rating;

    /**
     * 상품 정보를 생성하는 생성자
     * 
     * @param id 상품 고유 ID
     * @param name 상품명
     * @param description 상품 설명
     * @param price 상품 가격 (원)
     * @param imageUrl 상품 이미지 URL
     * @param rating 상품 평점 (1.0 ~ 5.0)
     */
    public ProductDto(Long id, String name, String description, int price, String imageUrl, double rating) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.rating = rating;
    }

    // ===== Getter 메서드들 =====
    // JSON 직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 상품 ID를 반환
     * @return 상품 고유 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 상품명을 반환
     * @return 상품명
     */
    public String getName() {
        return name;
    }

    /**
     * 상품 설명을 반환
     * @return 상품 설명
     */
    public String getDescription() {
        return description;
    }

    /**
     * 상품 가격을 반환
     * @return 상품 가격 (원)
     */
    public int getPrice() {
        return price;
    }

    /**
     * 상품 이미지 URL을 반환
     * @return 상품 이미지 URL
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     * 상품 평점을 반환
     * @return 상품 평점 (1.0 ~ 5.0)
     */
    public double getRating() {
        return rating;
    }

    // ===== Setter 메서드들 =====
    // JSON 역직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 상품 ID를 설정
     * @param id 상품 고유 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 상품명을 설정
     * @param name 상품명
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 상품 설명을 설정
     * @param description 상품 설명
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 상품 가격을 설정
     * @param price 상품 가격 (원)
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 상품 이미지 URL을 설정
     * @param imageUrl 상품 이미지 URL
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     * 상품 평점을 설정
     * @param rating 상품 평점 (1.0 ~ 5.0)
     */
    public void setRating(double rating) {
        this.rating = rating;
    }
} 