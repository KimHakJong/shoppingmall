# 🛍️ HPIX 쇼핑몰 프로젝트 공통 규칙

## 📁 프로젝트 구조

```
shoppingmall/
├── backend/                 # Spring Boot 백엔드
│   ├── src/main/java/com/shopping/backend/
│   │   ├── config/         # 설정 클래스
│   │   ├── controller/     # REST API 컨트롤러
│   │   ├── dto/           # 데이터 전송 객체
│   │   ├── entity/        # JPA 엔티티
│   │   ├── jwt/           # JWT 관련 클래스
│   │   ├── mapper/        # MyBatis 매퍼
│   │   ├── repository/    # JPA 리포지토리
│   │   └── service/       # 비즈니스 로직
│   └── src/main/resources/
│       ├── mapper/        # MyBatis XML 매퍼
│       └── application.yml # 설정 파일
├── frontend/               # React + TypeScript 프론트엔드
│   ├── src/
│   │   ├── components/    # 재사용 가능한 컴포넌트
│   │   ├── *.tsx         # 페이지 컴포넌트
│   │   └── *.css         # 스타일 파일
│   └── package.json
└── *.sql                  # 데이터베이스 스크립트
```

## 🎯 기술 스택

### Backend
- **Framework**: Spring Boot 2.7.18
- **Language**: Java 8
- **Database**: MySQL 8.0 + H2 (개발용)
- **ORM**: JPA/Hibernate + MyBatis
- **Security**: Spring Security + JWT
- **Build Tool**: Maven
- **Lombok**: 코드 간소화

### Frontend
- **Framework**: React 19.1.0
- **Language**: TypeScript 4.9.5
- **Router**: React Router DOM 7.7.1
- **HTTP Client**: Axios 1.11.0
- **Build Tool**: Vite 7.0.4
- **Linting**: ESLint 9.30.1

## 📝 코딩 규칙

### 1. 네이밍 컨벤션

#### Java (Backend)
```java
// 클래스명: PascalCase
public class UsersController { }

// 메서드명: camelCase
public ResponseEntity joinUser() { }

// 변수명: camelCase
private String userId;

// 상수명: UPPER_SNAKE_CASE
public static final String DEFAULT_ROLE = "USER";

// 패키지명: lowercase
package com.shopping.backend.controller;
```

#### TypeScript/JavaScript (Frontend)
```typescript
// 컴포넌트명: PascalCase
function Header() { }

// 함수명: camelCase
const handleMenuClick = () => { };

// 변수명: camelCase
const [isSidebarOpen, setIsSidebarOpen] = useState(false);

// CSS 클래스명: kebab-case
.sidebar-overlay { }
```

### 2. 파일 구조 규칙

#### Backend
```
controller/     # REST API 엔드포인트
├── UsersController.java
├── MainController.java
├── MenuController.java
└── SimpleTestController.java

entity/         # JPA 엔티티 (테이블과 1:1 매핑)
├── Users.java
└── Menu.java

dto/           # API 요청/응답용 DTO
├── LoginRequest.java
└── LoginResponse.java

service/       # 비즈니스 로직
├── UsersService.java
└── MenuService.java

repository/    # JPA 리포지토리
├── UsersRepository.java
└── MenuRepository.java

mapper/        # MyBatis 매퍼 (설정만 유지)
```

#### Frontend
```
src/
├── components/          # 재사용 가능한 컴포넌트
│   ├── Sidebar.tsx
│   └── Sidebar.css
├── pages/              # 페이지 컴포넌트 (권장)
│   ├── Login.tsx
│   ├── SignUp.tsx
│   └── MainPage.tsx
└── styles/             # 공통 스타일 (권장)
    ├── common.css
    └── variables.css
```

### 3. 주석 규칙

#### Java
```java
/**
 * 회원가입 API
 * @param user 회원 정보
 * @return ResponseEntity 회원가입 결과
 */
@PostMapping("/join")
public ResponseEntity joinUser(@RequestBody Users user) {
    // 필수 필드 검증
    if (user.getUserId() == null || user.getUserId().trim().isEmpty()) {
        return ResponseEntity.badRequest().body("아이디는 필수입니다.");
    }
    
    // 비즈니스 로직 처리
    usersService.insertUser(user);
    return ResponseEntity.ok("회원가입이 정상적으로 완료되었습니다.");
}
```

#### TypeScript
```typescript
/**
 * 헤더 컴포넌트
 * HPIX 쇼핑몰과 비슷한 헤더 디자인
 */
function Header() {
  // 사이드바 상태 관리
  const [isSidebarOpen, setIsSidebarOpen] = useState(false);

  // 메뉴 클릭 핸들러
  const handleMenuClick = () => {
    setIsSidebarOpen(true);
  };

  return (
    // JSX 내용
  );
}
```

### 4. CSS 규칙

#### 클래스명 규칙
```css
/* 컴포넌트별 접두사 사용 */
.header-container { }
.header-content { }
.header-icons { }

.sidebar-overlay { }
.sidebar-menu { }
.sidebar-content { }

/* 상태 클래스 */
.is-active { }
.is-disabled { }
.is-loading { }

/* 유틸리티 클래스 */
.text-center { }
.mt-20 { }
.p-10 { }
```

#### 반응형 디자인
```css
/* 모바일 퍼스트 접근법 */
.sidebar-menu {
  width: 100%;
}

@media (min-width: 768px) {
  .sidebar-menu {
    width: 320px;
  }
}

@media (min-width: 1024px) {
  .sidebar-menu {
    width: 350px;
  }
}
```

### 5. API 규칙

#### REST API 엔드포인트 (모든 요청을 POST로 통일)
```
POST   /api/users/profile   # 사용자 조회
POST   /api/users/join      # 회원가입
POST   /api/users/login     # 로그인
POST   /api/users/update    # 사용자 수정
POST   /api/users/delete    # 사용자 삭제
POST   /api/main            # 메인 페이지 데이터 조회
POST   /api/main/health     # 서버 상태 확인
POST   /api/menus/all       # 모든 메뉴 조회
POST   /api/menus/top-level # 최상위 메뉴 조회
POST   /api/menus/{menuId}  # 특정 메뉴 조회
POST   /api/menus/create    # 메뉴 생성
POST   /api/menus/update/{menuId} # 메뉴 수정
POST   /api/menus/delete/{menuId} # 메뉴 삭제
POST   /api/simple/test     # 간단한 테스트
```

#### 응답 형식
```json
{
  "success": true,
  "message": "처리 완료",
  "data": {
    "userId": "user123",
    "userName": "홍길동"
  }
}
```

### 6. 상태 관리 규칙

#### React Hooks 사용
```typescript
// 로컬 상태
const [isOpen, setIsOpen] = useState(false);

// 복잡한 상태
const [formData, setFormData] = useState({
  userId: '',
  password: '',
  email: ''
});

// 사이드 이펙트
useEffect(() => {
  // 컴포넌트 마운트 시 실행
  return () => {
    // 컴포넌트 언마운트 시 정리
  };
}, [dependencies]);
```

### 7. 에러 처리 규칙

#### Backend
```java
try {
    usersService.insertUser(user);
    return ResponseEntity.ok("성공");
} catch (IllegalArgumentException e) {
    return ResponseEntity.badRequest().body(e.getMessage());
} catch (Exception e) {
    log.error("회원가입 실패", e);
    return ResponseEntity.internalServerError().body("서버 오류");
}
```

#### Frontend
```typescript
// 모든 API 호출은 POST로 통일
try {
  const response = await axios.post('/api/users/join', userData);
  setMessage('회원가입 성공');
} catch (error) {
  if (axios.isAxiosError(error)) {
    setError(error.response?.data || '회원가입 실패');
  }
}

// 데이터 조회도 POST로 통일
try {
  const response = await axios.post('/api/main', {});
  setMainData(response.data);
} catch (error) {
  if (axios.isAxiosError(error)) {
    setError('데이터 조회 실패');
  }
}
```

### 8. 보안 규칙

#### JWT 토큰 관리
```java
// 토큰 생성
String accessToken = jwtTokenizer.createAccessToken(userId, role);
String refreshToken = jwtTokenizer.createRefreshToken(userId);

// 토큰 검증
if (!jwtTokenizer.validateToken(token)) {
    throw new SecurityException("유효하지 않은 토큰");
}
```

#### 비밀번호 암호화
```java
// 비밀번호 인코딩
user.setUserPassword(bCryptPasswordEncoder.encode(password));

// 비밀번호 검증
if (!passwordEncoder.matches(inputPassword, storedPassword)) {
    return ResponseEntity.badRequest().body("비밀번호가 일치하지 않습니다.");
}
```

### 9. MyBatis와 JPA 사용 규칙

#### 클래스 명명 규칙
```java
// JPA 엔티티 (테이블명과 동일, 단수형)
@Entity
@Table(name = "users")
public class Users { }  // 테이블명: users

@Entity
@Table(name = "menus")
public class Menu { }   // 테이블명: menus

// API 요청/응답용 DTO
public class LoginRequest { } // 로그인 요청
public class LoginResponse { } // 로그인 응답
```

#### 사용 목적별 구분
```java
// JPA: CRUD 작업, 엔티티 관리
@Repository
public interface UsersRepository extends JpaRepository<Users, String> {
    Users findByUserId(String userId);
}

// 현재 프로젝트는 JPA만 사용
// MyBatis 설정은 유지하되 실제 사용하지 않음
```

#### 패키지 구조
```
entity/     # JPA 엔티티 (테이블과 1:1 매핑)
dto/        # API 요청/응답용 DTO
repository/ # JPA 리포지토리 (Repository 패턴)
mapper/     # MyBatis 매퍼 (설정만 유지)
```

#### 현재 프로젝트 구조
- **JPA**: 실제 사용 (CRUD 작업)
- **MyBatis**: 설정만 유지 (향후 필요시 사용 가능)
- **DTO**: API 요청/응답용으로만 사용

### 10. 데이터베이스 규칙

#### 테이블 명명
```sql
-- 테이블명: snake_case, 복수형
CREATE TABLE users (
  user_id VARCHAR(20) PRIMARY KEY,
  user_name VARCHAR(50) NOT NULL,
  created_tsp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE menus (
  menu_id VARCHAR(10) PRIMARY KEY,
  menu_name VARCHAR(100) NOT NULL,
  parent_menu_id VARCHAR(10),
  menu_depth INT NOT NULL,
  menu_order INT NOT NULL,
  usg_yn CHAR(1) NOT NULL DEFAULT 'Y',
  created_tsp TIMESTAMP NOT NULL,
  updated_tsp TIMESTAMP NOT NULL
);
```

#### 컬럼 명명
```sql
-- 컬럼명: snake_case
user_id, user_name, user_password, created_tsp, updated_tsp
menu_id, menu_name, parent_menu_id, menu_depth, menu_order, usg_yn
```

### 11. Git 규칙

#### 브랜치 명명
```
main                    # 메인 브랜치
develop                 # 개발 브랜치
feature/user-login      # 기능 브랜치
hotfix/critical-bug     # 긴급 수정 브랜치
```

#### 커밋 메시지
```
feat: 사용자 로그인 기능 추가
fix: 회원가입 시 비밀번호 검증 오류 수정
docs: API 문서 업데이트
style: 코드 포맷팅 수정
refactor: 사용자 서비스 로직 리팩토링
test: 로그인 테스트 케이스 추가
```

## 🚀 개발 환경 설정

### Backend 실행
```bash
cd shoppingmall/backend
mvn clean compile
mvn spring-boot:run
```

### Frontend 실행
```bash
cd shoppingmall/frontend
npm install
npm run dev
```

### API 호출 규칙
```bash
# 모든 API 호출은 POST 메서드 사용
curl -X POST http://localhost:8080/api/main
curl -X POST http://localhost:8080/api/users/login -H "Content-Type: application/json" -d '{"userId":"test","password":"test"}'
```

### 데이터베이스 설정
```bash
# MySQL 실행
mysql -u root -p

# 데이터베이스 생성
CREATE DATABASE shopping_mall;

# 테이블 생성
source create_tables.sql;
```

## 📋 체크리스트

### 코드 리뷰 시 확인사항
- [ ] 네이밍 컨벤션 준수
- [ ] 주석 작성 완료
- [ ] 에러 처리 구현
- [ ] 보안 검증 완료
- [ ] 반응형 디자인 적용
- [ ] 모든 API 호출이 POST 메서드 사용
- [ ] 테스트 코드 작성 (권장)
- [ ] Git 커밋 메시지 규칙 준수

### 배포 전 확인사항
- [ ] 환경 변수 설정
- [ ] 데이터베이스 마이그레이션
- [ ] API 문서 업데이트
- [ ] 성능 테스트
- [ ] 보안 검사
- [ ] 브라우저 호환성 확인

---

**마지막 업데이트**: 2024년 12월
**버전**: 1.0.0
