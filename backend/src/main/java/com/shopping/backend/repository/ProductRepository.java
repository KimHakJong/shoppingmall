package com.shopping.backend.repository;

import com.shopping.backend.dto.SearchProductsResponse;
import com.shopping.backend.entity.Product;

import org.apache.ibatis.annotations.Param;
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

    @Query(value =  "select A.prod_id as prodId, A.prod_name as prodName, A.menu_id as menuId, A.prod_price as prodPrice, B.image_url as imageUrl " +
                    "from product A left outer join product_image B on A.prod_id = B.prod_id " +
                    "where A.menu_id = :menuId " +
                    "and A.prod_status = :prodStatus " +
                    "and B.thumbnail_yn = :thumbnailYn"
                    , nativeQuery = true)
    List<SearchProductsResponse> searchProductsList(@Param("menuId") String menuId,
                                                    @Param("prodStatus") String prodStatus,
                                                    @Param("thumbnailYn") String thumbnailYn
                            );
    
} 