# JWT 토큰 인증 시스템 가이드

## 개요
이 프로젝트는 JWT(JSON Web Token)를 사용한 인증 시스템을 구현한 쇼핑몰 백엔드입니다.

## 구현된 기능

### 1. 회원가입
- **엔드포인트**: `POST /api/users/join`
- **요청 본문**:
```json
{
    "userId": "testuser",
    "userPassword": "password123",
    "userName": "테스트 사용자",
    "userEmail": "test@example.com"
}
```

### 2. 로그인
- **엔드포인트**: `POST /api/users/login`
- **요청 본문**:
```json
{
    "userId": "testuser",
    "userPassword": "password123"
}
```
- **응답**:
```json
{
    "userId": "testuser",
    "userRole": "USER",
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "refreshToken": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "로그인이 성공했습니다."
}
```

### 3. 토큰 갱신
- **엔드포인트**: `POST /api/users/refresh`
- **헤더**: `Authorization: Bearer {refreshToken}`
- **응답**:
```json
{
    "accessToken": "eyJhbGciOiJIUzI1NiJ9...",
    "message": "토큰이 갱신되었습니다."
}
```

### 4. 보호된 리소스 접근
- **엔드포인트**: `GET /api/users/profile`
- **헤더**: `Authorization: Bearer {accessToken}`
- **응답**: "인증된 사용자만 접근 가능한 프로필 페이지입니다."

## 토큰 정보

### Access Token
- **유효기간**: 1시간
- **용도**: API 요청 시 인증
- **포함 정보**: 사용자 ID, 역할

### Refresh Token
- **유효기간**: 7일
- **용도**: Access Token 갱신
- **포함 정보**: 사용자 ID

## 보안 설정

### 허용된 엔드포인트 (인증 불필요)
- `/api/users/join` - 회원가입
- `/api/users/login` - 로그인
- `/api/users/refresh` - 토큰 갱신

### 보호된 엔드포인트 (인증 필요)
- 그 외 모든 엔드포인트

## 사용 방법

### 1. 회원가입
```bash
curl -X POST http://localhost:8080/api/users/join \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "testuser",
    "userPassword": "password123",
    "userName": "테스트 사용자",
    "userEmail": "test@example.com"
  }'
```

### 2. 로그인
```bash
curl -X POST http://localhost:8080/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "userId": "testuser",
    "userPassword": "password123"
  }'
```

### 3. 보호된 리소스 접근
```bash
curl -X GET http://localhost:8080/api/users/profile \
  -H "Authorization: Bearer {accessToken}"
```

### 4. 토큰 갱신
```bash
curl -X POST http://localhost:8080/api/users/refresh \
  -H "Authorization: Bearer {refreshToken}"
```

## 기술 스택

- **Spring Boot**: 2.7.18
- **Spring Security**: 인증 및 권한 관리
- **JWT**: JSON Web Token 라이브러리 (jjwt 0.11.5)
- **MySQL**: 데이터베이스
- **JPA/Hibernate**: ORM
- **BCrypt**: 비밀번호 암호화

## 주의사항

1. **토큰 보안**: Access Token과 Refresh Token을 안전하게 저장하세요.
2. **토큰 만료**: Access Token이 만료되면 Refresh Token으로 갱신하세요.
3. **HTTPS**: 프로덕션 환경에서는 반드시 HTTPS를 사용하세요.
4. **비밀번호**: 강력한 비밀번호를 사용하세요.
