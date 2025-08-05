package com.shopping.backend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 쇼핑몰 백엔드 메인 애플리케이션 클래스
 * 
 * @SpringBootApplication 어노테이션은 다음을 포함합니다:
 * - @Configuration: 이 클래스가 Bean 설정 클래스임을 나타냄
 * - @EnableAutoConfiguration: 클래스패스에 있는 의존성을 기반으로 자동 설정
 * - @ComponentScan: 현재 패키지와 하위 패키지에서 컴포넌트를 스캔
 */
@SpringBootApplication
@MapperScan("com.shopping.backend.mapper")
public class ShoppingBackendApplication {

    /**
     * 애플리케이션의 진입점
     * Spring Boot 애플리케이션을 시작합니다.
     * 
     * @param args 명령행 인수
     */
    public static void main(String[] args) {
        // SpringApplication.run()을 통해 내장 톰캣 서버가 시작됩니다
        // 기본 포트는 8080입니다 (application.yml에서 변경 가능)
        SpringApplication.run(ShoppingBackendApplication.class, args);
    }

} 