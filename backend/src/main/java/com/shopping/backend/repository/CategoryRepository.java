package com.shopping.backend.repository;

import com.shopping.backend.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 카테고리 데이터 접근을 위한 Repository 인터페이스
 * 
 * @Repository: Spring이 이 인터페이스를 Repository Bean으로 등록
 * JpaRepository<Category, Long>: Category 엔티티, ID 타입이 Long
 * 
 * JpaRepository에서 제공하는 기본 메서드들:
 * - save(): 저장/수정
 * - findById(): ID로 조회
 * - findAll(): 전체 조회
 * - delete(): 삭제
 * - count(): 개수 조회
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    /**
     * 카테고리 코드로 카테고리 조회
     * 
     * @param code 카테고리 코드
     * @return Optional<Category> 카테고리 (없으면 empty)
     */
    Optional<Category> findByCode(String code);
    
    /**
     * 카테고리 이름으로 카테고리 조회
     * 
     * @param name 카테고리 이름
     * @return Optional<Category> 카테고리 (없으면 empty)
     */
    Optional<Category> findByName(String name);
    
    /**
     * 활성화된 카테고리 목록 조회 (생성일시 순)
     * 
     * @return List<Category> 카테고리 목록
     */
    List<Category> findAllByOrderByCreatedAtAsc();
    
    /**
     * 카테고리 코드 존재 여부 확인
     * 
     * @param code 카테고리 코드
     * @return boolean 존재 여부
     */
    boolean existsByCode(String code);
} 