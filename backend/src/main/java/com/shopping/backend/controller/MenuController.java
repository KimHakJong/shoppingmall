package com.shopping.backend.controller;

import com.shopping.backend.constant.ErrorCode;
import com.shopping.backend.dto.ApiResponse;
import com.shopping.backend.dto.SearchAllMenuResponse;
import com.shopping.backend.entity.Menu;
import com.shopping.backend.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

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
    @PostMapping("/searchall")
    public ResponseEntity<ApiResponse<List<SearchAllMenuResponse>>> searchAllMenus() {
        try {
            System.out.println("searchAllMenus 호출");
            List<SearchAllMenuResponse> menus = menuService.searchAllMenus();
            
            ApiResponse<List<SearchAllMenuResponse>> response = ApiResponse.success(
                "메뉴 목록을 성공적으로 조회했습니다.", 
                menus, 
                HttpStatus.OK.value()
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            System.out.println("searchAllMenus 오류: " + e.getMessage());
            
            ApiResponse<List<SearchAllMenuResponse>> response = ApiResponse.error(
                "메뉴 목록 조회에 실패했습니다: " + e.getMessage(),
                ErrorCode.MENU_SEARCH_ERROR,
                HttpStatus.BAD_REQUEST.value()
            );
            
            return ResponseEntity.badRequest().body(response);
        }
    }
}
