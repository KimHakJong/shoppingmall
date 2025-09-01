package com.shopping.backend.dto;

/**
 * 메뉴별 상품 조회 요청 DTO
 */
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;  // 기본 생성자

@Getter
@Setter
@NoArgsConstructor  // 기본 생성자
public class SearchProductsRequest {
    private String menuId;

}
