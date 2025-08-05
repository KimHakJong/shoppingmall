package com.shopping.backend.mapper;

import com.shopping.backend.entity.Category;
import com.shopping.backend.dto.CategoryStatsDto;
import com.shopping.backend.dto.CategoryPriceStatsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 카테고리 MyBatis Mapper 인터페이스
 * 
 * @Mapper: MyBatis가 이 인터페이스를 구현체로 생성
 * 
 * JPA와 MyBatis 혼용 전략:
 * - JPA: 기본 CRUD, 엔티티 관리
 * - MyBatis: 복잡한 쿼리, 통계, 동적 쿼리
 */
@Mapper
public interface CategoryMapper {
    
    /**
     * 모든 카테고리 조회 (XML 기반)
     * 
     * @return 카테고리 목록
     */
    List<Category> findAllCategories();
    
    /**
     * 카테고리 코드로 조회
     * 
     * @param code 카테고리 코드
     * @return 카테고리
     */
    Category findByCode(@Param("code") String code);
    
    /**
     * 카테고리 이름으로 검색
     * 
     * @param name 검색할 이름
     * @return 카테고리 목록
     */
    List<Category> findByNameContaining(@Param("name") String name);
    
    /**
     * 카테고리별 통계 조회 (MyBatis 장점 - 복잡한 JOIN 쿼리)
     * 
     * @return 카테고리 통계 목록
     */
    List<CategoryStatsDto> getCategoryStats();
    
    /**
     * 카테고리별 가격 통계 조회
     * 
     * @return 카테고리 가격 통계 목록
     */
    List<CategoryPriceStatsDto> getCategoryPriceStats();
} 