package com.shopping.backend.mapper;

import com.shopping.backend.entity.Product;
import com.shopping.backend.dto.ProductStatsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 상품 MyBatis Mapper 인터페이스
 * 
 * @Mapper: MyBatis가 이 인터페이스를 구현체로 생성
 * 
 * JPA와 MyBatis 혼용 전략:
 * - JPA: 기본 CRUD, 엔티티 관리
 * - MyBatis: 복잡한 쿼리, 통계, 동적 쿼리
 */
@Mapper
public interface ProductMapper {
    
    /**
     * 모든 상품 조회 (XML 기반)
     * 
     * @return 상품 목록
     */
    List<Product> findAllProducts();
    
    /**
     * 상품 ID로 조회
     * 
     * @param id 상품 ID
     * @return 상품
     */
    Product findById(@Param("id") Long id);
    
    /**
     * 카테고리별 상품 조회
     * 
     * @param categoryId 카테고리 ID
     * @return 상품 목록
     */
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);
    
    /**
     * 상품명으로 검색
     * 
     * @param name 검색할 상품명
     * @return 상품 목록
     */
    List<Product> findByNameContaining(@Param("name") String name);
    
    /**
     * 인기 상품 조회
     * 
     * @return 인기 상품 목록
     */
    List<Product> findPopularProducts();
    
    /**
     * 신상품 조회
     * 
     * @return 신상품 목록
     */
    List<Product> findNewProducts();
    
    /**
     * 가격 범위로 상품 검색
     * 
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 상품 목록
     */
    List<Product> findByPriceRange(@Param("minPrice") Integer minPrice, @Param("maxPrice") Integer maxPrice);
    
    /**
     * 상품 통계 조회 (MyBatis 장점 - 복잡한 집계 쿼리)
     * 
     * @return 상품 통계
     */
    ProductStatsDto getProductStats();
} 