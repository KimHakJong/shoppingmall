package com.shopping.backend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 웹 설정 클래스 - CORS(Cross-Origin Resource Sharing) 설정
 * 
 * @Configuration: 이 클래스가 Spring 설정 클래스임을 나타냄
 * - Spring이 이 클래스를 Bean으로 등록하고 설정을 적용함
 * 
 * WebMvcConfigurer: Spring MVC의 설정을 커스터마이징하기 위한 인터페이스
 * - addCorsMappings(): CORS 설정을 추가하는 메서드
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS 설정을 추가하는 메서드
     * 
     * CORS란? Cross-Origin Resource Sharing
     * - 브라우저의 보안 정책으로, 다른 도메인/포트에서의 요청을 제한
     * - 프론트엔드(React: 5173포트)에서 백엔드(Spring: 8080포트)로 API 호출 시 필요
     * 
     * @param registry CORS 설정을 등록하는 객체
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 모든 URL 패턴에 CORS 적용
                .allowedOrigins("http://localhost:3000")  // React 개발 서버 허용
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // 허용할 HTTP 메서드
                .allowedHeaders("*")  // 모든 헤더 허용 (Content-Type, Authorization 등)
                .allowCredentials(true)  // 쿠키, 인증 헤더 등 민감한 정보 포함 허용
                .maxAge(3600);  // CORS 설정을 1시간 동안 캐시 (성능 향상)
    }
} 