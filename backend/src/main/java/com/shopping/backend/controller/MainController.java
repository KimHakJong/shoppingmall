package com.shopping.backend.controller;

import com.shopping.backend.dto.MainPageResponse;
import com.shopping.backend.dto.ProductDto;
import com.shopping.backend.dto.CategoryDto;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
     * @GetMapping: HTTP GET 요청을 처리
     * - URL: http://localhost:8080/api/main
     * - 프론트엔드에서 이 API를 호출하여 메인 페이지 데이터를 가져옴
     * 
     * @return MainPageResponse 메인 페이지에 필요한 모든 데이터
     *         - 카테고리 목록
     *         - 인기 상품 목록  
     *         - 신상품 목록
     */
    @GetMapping
    public MainPageResponse getMainPage() {
        // 카테고리 목록 생성 (실제로는 데이터베이스에서 조회)
        List<CategoryDto> categories = Arrays.asList(
            new CategoryDto(1L, "전자제품", "electronics"),
            new CategoryDto(2L, "의류", "clothing"),
            new CategoryDto(3L, "가전제품", "appliances"),
            new CategoryDto(4L, "스포츠용품", "sports")
        );

        // 인기 상품 목록 생성 (실제로는 판매량 기준으로 조회)
        List<ProductDto> popularProducts = Arrays.asList(
            new ProductDto(1L, "스마트폰", "최신 스마트폰", 800000, "https://via.placeholder.com/200x200?text=Phone", 4.5),
            new ProductDto(2L, "노트북", "고성능 노트북", 1200000, "https://via.placeholder.com/200x200?text=Laptop", 4.8),
            new ProductDto(3L, "헤드폰", "무선 헤드폰", 150000, "https://via.placeholder.com/200x200?text=Headphone", 4.3),
            new ProductDto(4L, "스마트워치", "스마트워치", 300000, "https://via.placeholder.com/200x200?text=Watch", 4.6)
        );

        // 신상품 목록 생성 (실제로는 등록일 기준으로 조회)
        List<ProductDto> newProducts = Arrays.asList(
            new ProductDto(5L, "태블릿", "신제품 태블릿", 500000, "https://via.placeholder.com/200x200?text=Tablet", 4.7),
            new ProductDto(6L, "블루투스 스피커", "휴대용 스피커", 80000, "https://via.placeholder.com/200x200?text=Speaker", 4.2),
            new ProductDto(7L, "게이밍 마우스", "게이밍 마우스", 120000, "https://via.placeholder.com/200x200?text=Mouse", 4.4),
            new ProductDto(8L, "무선 키보드", "무선 키보드", 90000, "https://via.placeholder.com/200x200?text=Keyboard", 4.1)
        );

        // 모든 데이터를 하나의 응답 객체로 묶어서 반환
        return new MainPageResponse(categories, popularProducts, newProducts);
    }

    /**
     * 서버 상태를 확인하는 헬스 체크 API
     * 
     * @GetMapping("/health"): HTTP GET 요청을 처리
     * - URL: http://localhost:8080/api/main/health
     * - 프론트엔드나 모니터링 도구에서 서버 상태 확인용
     * 
     * @return String 서버가 정상 동작 중임을 나타내는 메시지
     */
    @GetMapping("/health")
    public String health() {
        return "Shopping Mall Backend is running!";
    }
} 