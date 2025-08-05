package com.shopping.backend.controller;

import com.shopping.backend.entity.Category;
import com.shopping.backend.entity.Product;
import com.shopping.backend.mapper.CategoryMapper;
import com.shopping.backend.mapper.ProductMapper;
import com.shopping.backend.mapper.SimpleMapper;
import com.shopping.backend.dto.CategoryStatsDto;
import com.shopping.backend.dto.ProductStatsDto;
import com.shopping.backend.dto.CategoryPriceStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * MyBatis 연동 테스트를 위한 컨트롤러
 *
 * @RestController: REST API 컨트롤러
 * @RequestMapping("/api/test"): 테스트 API 경로
 */
@RestController
@RequestMapping("/api/test")
public class MyBatisTestController {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private SimpleMapper simpleMapper;

    /**
     * MyBatis 연결 상태 확인
     *
     * @return 연결 상태 메시지
     */
    @GetMapping("/mybatis-status")
    public String checkMyBatisStatus() {
        try {
            // 간단한 테스트 먼저
            int testResult = simpleMapper.test();
            if (testResult != 1) {
                return "MyBatis 기본 연결 실패";
            }
            
            // 카테고리 매퍼 테스트
            List<Category> categories = categoryMapper.findAllCategories();
            return "MyBatis 연결 성공! 카테고리 개수: " + categories.size();
        } catch (Exception e) {
            return "MyBatis 연결 실패: " + e.getMessage();
        }
    }

    /**
     * 카테고리 목록 조회 (MyBatis)
     *
     * @return 카테고리 목록
     */
    @GetMapping("/categories")
    public List<Category> getCategories() {
        return categoryMapper.findAllCategories();
    }

    /**
     * 인기 상품 조회 (MyBatis)
     *
     * @return 인기 상품 목록
     */
    @GetMapping("/popular-products")
    public List<Product> getPopularProducts() {
        return productMapper.findPopularProducts();
    }

    /**
     * 신상품 조회 (Mybatis)
     *
     * @return 신상품 목록
     */
    @GetMapping("/new-products")
    public List<Product> getNewProducts() {
        return productMapper.findNewProducts();
    }

    /**
     * 모든 상품 조회 (MyBatis)
     *
     * @return 모든 상품 목록
     */
    @GetMapping("/all-products")
    public List<Product> getAllProducts() {
        return productMapper.findAllProducts();
    }

    /**
     * 카테고리 통계 조회 (MyBatis)
     *
     * @return 카테고리 통계
     */
    @GetMapping("/category-stats")
    public List<CategoryStatsDto> getCategoryStats() {
        return categoryMapper.getCategoryStats();
    }

    /**
     * 상품 통계 조회 (MyBatis)
     *
     * @return 상품 통계
     */
    @GetMapping("/product-stats")
    public ProductStatsDto getProductStats() {
        return productMapper.getProductStats();
    }

    /**
     * 카테고리별 가격 통계 (MyBatis)
     *
     * @return 카테고리별 가격 통계
     */
    @GetMapping("/category-price-stats")
    public List<CategoryPriceStatsDto> getCategoryPriceStats() {
        return categoryMapper.getCategoryPriceStats();
    }
} 