package com.shopping.backend.service;

import com.shopping.backend.entity.Menu;
import com.shopping.backend.repository.MenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public List<Menu> getAllActiveMenus() {
        return menuRepository.findByUsgYnOrderByMenuOrderAsc("Y");
    }

    /**
     * 최상위 메뉴 목록 조회
     * 
     * @return 최상위 메뉴 목록
     */
    public List<Menu> getTopLevelMenus() {
        return menuRepository.findTopLevelMenus();
    }



    /**
     * 특정 메뉴 ID로 메뉴 조회
     * 
     * @param menuId 메뉴 ID
     * @return 메뉴 정보
     */
    public Optional<Menu> getMenuById(String menuId) {
        return menuRepository.findById(menuId);
    }

    /**
     * 메뉴명으로 메뉴 조회
     * 
     * @param menuName 메뉴명
     * @return 메뉴 정보
     */
    public Optional<Menu> getMenuByName(String menuName) {
        return menuRepository.findByMenuName(menuName);
    }

    /**
     * 코드로 메뉴 조회
     * 
     * @param code 메뉴 코드
     * @return 메뉴 정보
     */
    public Optional<Menu> getMenuByCode(String code) {
        return menuRepository.findByCode(code);
    }

    /**
     * 특정 깊이의 메뉴 목록 조회
     * 
     * @param menuDepth 메뉴 깊이
     * @return 해당 깊이의 메뉴 목록
     */
    public List<Menu> getMenusByDepth(Integer menuDepth) {
        return menuRepository.findByMenuDepthOrderByMenuOrderAsc(menuDepth);
    }

    /**
     * 상위 메뉴 ID로 하위 메뉴 목록 조회
     * 
     * @param parentMenuId 상위 메뉴 ID
     * @return 하위 메뉴 목록
     */
    public List<Menu> getSubMenus(String parentMenuId) {
        return menuRepository.findByParentMenuIdOrderByMenuOrderAsc(parentMenuId);
    }

    /**
     * 관리자 메뉴 목록 조회
     * 
     * @return 관리자 메뉴 목록
     */
    public List<Menu> getAdminMenus() {
        return menuRepository.findByAdminYnOrderByMenuOrderAsc("Y");
    }

    /**
     * 메뉴 생성
     * 
     * @param menu 메뉴 정보
     * @return 생성된 메뉴
     */
    public Menu createMenu(Menu menu) {
        return menuRepository.save(menu);
    }

    /**
     * 메뉴 수정
     * 
     * @param menuId 메뉴 ID
     * @param menu 메뉴 정보
     * @return 수정된 메뉴
     */
    public Menu updateMenu(String menuId, Menu menu) {
        Optional<Menu> existingMenu = menuRepository.findById(menuId);
        if (existingMenu.isPresent()) {
            Menu updatedMenu = existingMenu.get();
            updatedMenu.setMenuName(menu.getMenuName());
            updatedMenu.setParentMenuId(menu.getParentMenuId());
            updatedMenu.setMenuDepth(menu.getMenuDepth());
            updatedMenu.setMenuOrder(menu.getMenuOrder());
            updatedMenu.setUsgYn(menu.getUsgYn());
            updatedMenu.setAdminYn(menu.getAdminYn());
            updatedMenu.setCode(menu.getCode());
            updatedMenu.setName(menu.getName());
            updatedMenu.setUpdatedId(menu.getUpdatedId());
            return menuRepository.save(updatedMenu);
        }
        throw new RuntimeException("메뉴를 찾을 수 없습니다: " + menuId);
    }

    /**
     * 메뉴 삭제 (사용 여부를 'N'으로 변경)
     * 
     * @param menuId 메뉴 ID
     * @param updatedId 수정자 ID
     */
    public void deleteMenu(String menuId, String updatedId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        if (menu.isPresent()) {
            Menu existingMenu = menu.get();
            existingMenu.setUsgYn("N");
            existingMenu.setUpdatedId(updatedId);
            menuRepository.save(existingMenu);
        } else {
            throw new RuntimeException("메뉴를 찾을 수 없습니다: " + menuId);
        }
    }


}
