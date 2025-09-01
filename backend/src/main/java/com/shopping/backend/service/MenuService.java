package com.shopping.backend.service;

import com.shopping.backend.dto.SearchAllMenuResponse;
import com.shopping.backend.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 메뉴 서비스 클래스
 * 메뉴 관련 비즈니스 로직을 처리
 */
@Service
@Transactional
public class MenuService {

    private final MenuRepository menuRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    /**
     * 모든 사용 가능한 메뉴 조회
     * 
     * @return 사용 가능한 메뉴 목록
     */
    public List<SearchAllMenuResponse> searchAllMenus() {
        System.out.println("MenuService.searchAllMenus 서비스 호출");
        String menu_usg_yn = "Y";
        return menuRepository.searchAllMenus(menu_usg_yn);
    }

}
