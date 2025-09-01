package com.shopping.backend.repository;

import com.shopping.backend.dto.SearchAllMenuResponse;
import com.shopping.backend.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
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
     * 
     * @param X
     * @return
     */
    
    @Query(value = "WITH RECURSIVE menu_tree AS ( " +
                   "SELECT menu_id, menu_name, parent_menu_id, menu_depth, menu_order, " +
                   "CAST(LPAD(menu_order, 3, '0') AS CHAR(100)) AS path " +
                   "FROM menus WHERE parent_menu_id IS NULL AND usg_yn = ?1 " + // usg_yn 조건 추가
                   "UNION ALL " +
                   "SELECT m.menu_id, m.menu_name, m.parent_menu_id, m.menu_depth, m.menu_order, " +
                   "CONCAT(mt.path, '-', LPAD(m.menu_order, 1, '0')) AS path " +
                   "FROM menus m INNER JOIN menu_tree mt ON m.parent_menu_id = mt.menu_id " +
                   "WHERE m.usg_yn = ?1 " + // 하위 메뉴에도 usg_yn 조건 추가
                   ") " +
                   "SELECT menu_id as menuId, menu_name as menuName, parent_menu_id as parentMenuId, menu_depth as menuDepth, menu_order as menuOrder FROM menu_tree ORDER BY path"
                   , nativeQuery = true)
    List<SearchAllMenuResponse> searchAllMenus(String usgYn);

}
