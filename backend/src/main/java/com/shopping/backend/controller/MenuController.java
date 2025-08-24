package com.shopping.backend.controller;

import com.shopping.backend.entity.Menu;
import com.shopping.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * 메뉴 REST API 컨트롤러
 * 메뉴 관련 API 엔드포인트를 제공
 */
@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 모든 사용 가능한 메뉴 조회
     * 
     * @return 사용 가능한 메뉴 목록
     */
    @PostMapping("/all")
    public ResponseEntity<List<Menu>> getAllMenus() {
        try {
            List<Menu> menus = menuService.getAllActiveMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 최상위 메뉴 목록 조회
     * 
     * @return 최상위 메뉴 목록
     */
    @PostMapping("/top-level")
    public ResponseEntity<List<Menu>> getTopLevelMenus() {
        try {
            List<Menu> menus = menuService.getTopLevelMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }



    /**
     * 특정 메뉴 ID로 메뉴 조회
     * 
     * @param menuId 메뉴 ID
     * @return 메뉴 정보
     */
    @PostMapping("/{menuId}")
    public ResponseEntity<Menu> getMenuById(@PathVariable String menuId) {
        try {
            Optional<Menu> menu = menuService.getMenuById(menuId);
            return menu.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 메뉴명으로 메뉴 조회
     * 
     * @param menuName 메뉴명
     * @return 메뉴 정보
     */
    @PostMapping("/search/name")
    public ResponseEntity<Menu> getMenuByName(@RequestBody String menuName) {
        try {
            Optional<Menu> menu = menuService.getMenuByName(menuName);
            return menu.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 코드로 메뉴 조회
     * 
     * @param code 메뉴 코드
     * @return 메뉴 정보
     */
    @PostMapping("/search/code")
    public ResponseEntity<Menu> getMenuByCode(@RequestBody String code) {
        try {
            Optional<Menu> menu = menuService.getMenuByCode(code);
            return menu.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 특정 깊이의 메뉴 목록 조회
     * 
     * @param menuDepth 메뉴 깊이
     * @return 해당 깊이의 메뉴 목록
     */
    @PostMapping("/depth/{menuDepth}")
    public ResponseEntity<List<Menu>> getMenusByDepth(@PathVariable Integer menuDepth) {
        try {
            List<Menu> menus = menuService.getMenusByDepth(menuDepth);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 상위 메뉴 ID로 하위 메뉴 목록 조회
     * 
     * @param parentMenuId 상위 메뉴 ID
     * @return 하위 메뉴 목록
     */
    @PostMapping("/sub-menus")
    public ResponseEntity<List<Menu>> getSubMenus(@RequestBody String parentMenuId) {
        try {
            List<Menu> menus = menuService.getSubMenus(parentMenuId);
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 관리자 메뉴 목록 조회
     * 
     * @return 관리자 메뉴 목록
     */
    @PostMapping("/admin")
    public ResponseEntity<List<Menu>> getAdminMenus() {
        try {
            List<Menu> menus = menuService.getAdminMenus();
            return ResponseEntity.ok(menus);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 메뉴 생성
     * 
     * @param menu 메뉴 정보
     * @return 생성된 메뉴
     */
    @PostMapping("/create")
    public ResponseEntity<Menu> createMenu(@RequestBody Menu menu) {
        try {
            Menu createdMenu = menuService.createMenu(menu);
            return ResponseEntity.ok(createdMenu);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 메뉴 수정
     * 
     * @param menuId 메뉴 ID
     * @param menu 메뉴 정보
     * @return 수정된 메뉴
     */
    @PostMapping("/update/{menuId}")
    public ResponseEntity<Menu> updateMenu(@PathVariable String menuId, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(menuId, menu);
            return ResponseEntity.ok(updatedMenu);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * 메뉴 삭제 (사용 여부를 'N'으로 변경)
     * 
     * @param menuId 메뉴 ID
     * @param updatedId 수정자 ID
     * @return 삭제 결과
     */
    @PostMapping("/delete/{menuId}")
    public ResponseEntity<String> deleteMenu(@PathVariable String menuId, @RequestBody String updatedId) {
        try {
            menuService.deleteMenu(menuId, updatedId);
            return ResponseEntity.ok("메뉴가 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
