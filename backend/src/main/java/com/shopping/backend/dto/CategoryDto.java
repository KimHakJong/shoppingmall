package com.shopping.backend.dto;

/**
 * 카테고리 정보를 담는 DTO(Data Transfer Object) 클래스
 * 
 * 이 클래스는 상품 카테고리의 정보를 포함:
 * - 카테고리 ID, 이름, 코드
 * 
 * 예시:
 * - ID: 1, Name: "전자제품", Code: "electronics"
 * - ID: 2, Name: "의류", Code: "clothing"
 * 
 * JSON 직렬화/역직렬화를 위해 getter/setter 메서드가 필요
 */
public class CategoryDto {
    
    /** 카테고리 고유 ID */
    private Long id;
    
    /** 카테고리 이름 (예: "전자제품", "의류") */
    private String name;
    
    /** 카테고리 코드 (예: "electronics", "clothing") */
    private String code;

    /**
     * 카테고리 정보를 생성하는 생성자
     * 
     * @param id 카테고리 고유 ID
     * @param name 카테고리 이름
     * @param code 카테고리 코드
     */
    public CategoryDto(Long id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }

    // ===== Getter 메서드들 =====
    // JSON 직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 카테고리 ID를 반환
     * @return 카테고리 고유 ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 카테고리 이름을 반환
     * @return 카테고리 이름
     */
    public String getName() {
        return name;
    }

    /**
     * 카테고리 코드를 반환
     * @return 카테고리 코드
     */
    public String getCode() {
        return code;
    }

    // ===== Setter 메서드들 =====
    // JSON 역직렬화 시 사용됨 (Jackson 라이브러리가 자동으로 호출)
    
    /**
     * 카테고리 ID를 설정
     * @param id 카테고리 고유 ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 카테고리 이름을 설정
     * @param name 카테고리 이름
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 카테고리 코드를 설정
     * @param code 카테고리 코드
     */
    public void setCode(String code) {
        this.code = code;
    }
} 