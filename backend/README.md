# Shopping Mall Backend

스프링부트 기반의 쇼핑몰 백엔드 프로젝트입니다.

## 기술 스택

- **Spring Boot 2.7.18**
- **Java 8**
- **Spring Data JPA**
- **H2 Database** (개발용)
- **Maven**

## 프로젝트 구조

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/shopping/backend/
│   │   │   ├── ShoppingBackendApplication.java
│   │   │   ├── controller/
│   │   │   │   └── MainController.java
│   │   │   └── dto/
│   │   │       ├── MainPageResponse.java
│   │   │       ├── ProductDto.java
│   │   │       └── CategoryDto.java
│   │   └── resources/
│   │       └── application.yml
│   └── test/
└── pom.xml
```

## 실행 방법

### 1. Maven 설치 확인
```bash
mvn --version
```

### 2. 프로젝트 빌드
```bash
cd backend
mvn clean install
```

### 3. 애플리케이션 실행
```bash
mvn spring-boot:run
```

또는 IDE에서 `ShoppingBackendApplication.java`를 실행

## API 엔드포인트

### 메인 페이지 데이터 조회
- **GET** `/api/main`
- 응답: 카테고리, 인기 상품, 신상품 목록

### 헬스 체크
- **GET** `/api/main/health`
- 응답: 서버 상태 확인

### H2 데이터베이스 콘솔
- **URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:testdb`
- **Username**: `sa`
- **Password**: (비어있음)

## CORS 설정

프론트엔드(React)와의 통신을 위해 CORS가 설정되어 있습니다:
- 허용된 Origin: `http://localhost:5173` (React 개발 서버)

## 개발 환경

- **Java**: 8 이상
- **Maven**: 3.6 이상
- **포트**: 8080

## 다음 단계

1. 데이터베이스 엔티티 추가
2. 서비스 레이어 구현
3. 보안 설정 추가
4. 실제 데이터베이스 연결 (MySQL, PostgreSQL 등) 