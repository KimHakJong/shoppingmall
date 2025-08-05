package com.shopping.backend.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 간단한 테스트용 MyBatis 매퍼
 */
@Mapper
public interface SimpleMapper {
    
    /**
     * 간단한 테스트 쿼리
     */
    @Select("SELECT 1")
    int test();
} 