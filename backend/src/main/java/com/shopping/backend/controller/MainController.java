package com.shopping.backend.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 쇼핑몰 메인 페이지를 위한 REST API 컨트롤러
 * 
 * @RestController: 이 클래스가 REST API 컨트롤러임을 나타냄
 * - @Controller + @ResponseBody를 합친 어노테이션
 * - 모든 메서드의 반환값이 HTTP 응답 본문으로 직렬화됨
 * 
 * @RequestMapping("/api/main"): 이 컨트롤러의 기본 URL 경로
 * - 모든 메서드는 /api/main으로 시작하는 URL에 매핑됨
 */
@RestController
@RequestMapping("/api/main")
public class MainController {

    /**
     * 쇼핑몰 메인 페이지 데이터를 조회하는 API
     * 
     * @PostMapping: HTTP POST 요청을 처리
     * - URL: http://localhost:8080/api/main
     * - 프론트엔드에서 이 API를 호출하여 메인 페이지 데이터를 가져옴
     * 
     * @return MainPageResponse 메인 페이지에 필요한 모든 데이터
     *         - 카테고리 목록
     *         - 인기 상품 목록  
     *         - 신상품 목록
     */
    @PostMapping
    public Map<String, Object> getMainPage() {
        Map<String, Object> response = new HashMap<>();
        
        // 간단한 메인 페이지 데이터
        response.put("message", "메인 페이지 데이터");
        response.put("timestamp", System.currentTimeMillis());
        response.put("status", "success");
        
        return response;
    }

    /**
     * 서버 상태를 확인하는 헬스 체크 API
     * 
     * @PostMapping("/health"): HTTP POST 요청을 처리
     * - URL: http://localhost:8080/api/main/health
     * - 프론트엔드나 모니터링 도구에서 서버 상태 확인용
     * 
     * @return String 서버가 정상 동작 중임을 나타내는 메시지
     */
    @PostMapping("/health")
    public String health() {
        return "Shopping Mall Backend is running!";
    }
} 