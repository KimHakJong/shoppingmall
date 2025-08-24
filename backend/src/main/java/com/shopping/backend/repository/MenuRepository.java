package com.shopping.backend.repository;

import com.shopping.backend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 리포지토리 인터페이스
 * JPA를 사용한 메뉴 데이터 접근 계층
 */
@Repository
public interface MenuRepository extends JpaRepository<Menu, String> {

    /**
     * 사용 가능한 메뉴 목록 조회 (usg_yn = 'Y')
     * 
     * @return 사용 가능한 메뉴 목록
     */
    List<Menu> findByUsgYnOrderByMenuOrderAsc(String usgYn);

    /**
     * 특정 깊이의 메뉴 목록 조회
     * 
     * @param menuDepth 메뉴 깊이
     * @return 해당 깊이의 메뉴 목록
     */
    List<Menu> findByMenuDepthOrderByMenuOrderAsc(Integer menuDepth);

    /**
     * 상위 메뉴 ID로 하위 메뉴 목록 조회
     * 
     * @param parentMenuId 상위 메뉴 ID
     * @return 하위 메뉴 목록
     */
    List<Menu> findByParentMenuIdOrderByMenuOrderAsc(String parentMenuId);

    /**
     * 최상위 메뉴 목록 조회 (parent_menu_id가 null인 메뉴들)
     * 
     * @return 최상위 메뉴 목록
     */
    @Query("SELECT m FROM Menu m WHERE m.parentMenuId IS NULL AND m.usgYn = 'Y' ORDER BY m.menuOrder ASC")
    List<Menu> findTopLevelMenus();

    /**
     * 메뉴명으로 메뉴 조회
     * 
     * @param menuName 메뉴명
     * @return 메뉴 정보
     */
    Optional<Menu> findByMenuName(String menuName);

    /**
     * 코드로 메뉴 조회
     * 
     * @param code 메뉴 코드
     * @return 메뉴 정보
     */
    Optional<Menu> findByCode(String code);

    /**
     * 관리자 메뉴 목록 조회
     * 
     * @param adminYn 관리자 여부
     * @return 관리자 메뉴 목록
     */
    List<Menu> findByAdminYnOrderByMenuOrderAsc(String adminYn);

    /**
     * 특정 사용자가 생성한 메뉴 목록 조회
     * 
     * @param createdId 생성자 ID
     * @return 해당 사용자가 생성한 메뉴 목록
     */
    List<Menu> findByCreatedIdOrderByCreatedTspDesc(String createdId);

    /**
     * 메뉴 깊이와 사용 여부로 메뉴 목록 조회
     * 
     * @param menuDepth 메뉴 깊이
     * @param usgYn 사용 여부
     * @return 메뉴 목록
     */
    List<Menu> findByMenuDepthAndUsgYnOrderByMenuOrderAsc(Integer menuDepth, String usgYn);
}
