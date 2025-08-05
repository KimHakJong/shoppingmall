package com.shopping.backend.repository;

import com.shopping.backend.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 상품 데이터 접근을 위한 Repository 인터페이스
 * 
 * @Repository: Spring이 이 인터페이스를 Repository Bean으로 등록
 * JpaRepository<Product, Long>: Product 엔티티, ID 타입이 Long
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * 카테고리별 상품 목록 조회
     * 
     * @param categoryId 카테고리 ID
     * @return List<Product> 상품 목록
     */
    List<Product> findByCategoryId(Long categoryId);
    
    /**
     * 인기 상품 목록 조회 (인기 상품 플래그가 true인 상품들)
     * 
     * @return List<Product> 인기 상품 목록
     */
    @Query("SELECT p FROM Product p WHERE p.isPopular = true ORDER BY p.createdAt DESC")
    List<Product> findPopularProducts();
    
    /**
     * 신상품 목록 조회 (최근 등록 순, 최대 8개)
     * 
     * @return List<Product> 신상품 목록
     */
    @Query("SELECT p FROM Product p ORDER BY p.createdAt DESC")
    List<Product> findNewProducts();
    
    /**
     * 상품명으로 상품 검색
     * 
     * @param name 상품명 (부분 검색)
     * @return List<Product> 검색된 상품 목록
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * 가격 범위로 상품 검색
     * 
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return List<Product> 검색된 상품 목록
     */
    List<Product> findByPriceBetween(Integer minPrice, Integer maxPrice);
} 