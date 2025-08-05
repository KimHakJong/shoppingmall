# MySQL 데이터베이스 설정 가이드

## 🗄️ **1. MySQL 설치 및 설정**

### **MySQL 8.0 설치**
1. **MySQL Community Server 다운로드**: https://dev.mysql.com/downloads/mysql/
2. **설치 시 root 비밀번호 설정**: `1234` (application-mysql.yml과 일치)

### **MySQL 서비스 시작**
```bash
# Windows
net start mysql80

# 또는 MySQL Workbench에서 연결
```

## 🗄️ **2. 데이터베이스 생성**

### **MySQL 명령어로 데이터베이스 생성**
```sql
-- MySQL에 접속
mysql -u root -p

-- 비밀번호 입력: 1234

-- 데이터베이스 생성
CREATE DATABASE shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 데이터베이스 확인
SHOW DATABASES;

-- 사용할 데이터베이스 선택
USE shopping_mall;
```

### **또는 MySQL Workbench에서**
1. **MySQL Workbench 실행**
2. **새 연결 생성**:
   - Hostname: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `1234`
3. **SQL Editor에서 실행**:
   ```sql
   CREATE DATABASE shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

## 🗄️ **3. 애플리케이션 설정**

### **MySQL 프로파일로 실행**
```bash
# MySQL 설정으로 스프링부트 실행
mvn spring-boot:run -Dspring.profiles.active=mysql
```

### **또는 application.yml 수정**
```yaml
spring:
  profiles:
    active: mysql
```

## 🗄️ **4. 테이블 자동 생성**

### **JPA가 자동으로 테이블 생성**
- `ddl-auto: update` 설정으로 테이블이 자동 생성됨
- 애플리케이션 실행 시 다음 테이블들이 생성됨:
  - `categories`: 카테고리 테이블
  - `products`: 상품 테이블

### **생성된 테이블 확인**
```sql
-- 테이블 목록 확인
SHOW TABLES;

-- 카테고리 테이블 구조 확인
DESCRIBE categories;

-- 상품 테이블 구조 확인
DESCRIBE products;
```

## 🗄️ **5. 테스트 데이터 삽입**

### **카테고리 데이터 삽입**
```sql
INSERT INTO categories (name, code, created_at, updated_at) VALUES
('전자제품', 'electronics', NOW(), NOW()),
('의류', 'clothing', NOW(), NOW()),
('가전제품', 'appliances', NOW(), NOW()),
('스포츠용품', 'sports', NOW(), NOW());
```

### **상품 데이터 삽입**
```sql
INSERT INTO products (name, description, price, image_url, rating, category_id, created_at, updated_at) VALUES
('스마트폰', '최신 스마트폰', 800000, 'https://via.placeholder.com/200x200?text=Phone', 4.5, 1, NOW(), NOW()),
('노트북', '고성능 노트북', 1200000, 'https://via.placeholder.com/200x200?text=Laptop', 4.8, 1, NOW(), NOW()),
('헤드폰', '무선 헤드폰', 150000, 'https://via.placeholder.com/200x200?text=Headphone', 4.3, 1, NOW(), NOW()),
('스마트워치', '스마트워치', 300000, 'https://via.placeholder.com/200x200?text=Watch', 4.6, 1, NOW(), NOW());
```

## 🗄️ **6. 연결 테스트**

### **애플리케이션 실행**
```bash
cd backend
mvn spring-boot:run -Dspring.profiles.active=mysql
```

### **API 테스트**
```bash
# 헬스 체크
curl http://localhost:8080/api/main/health

# 메인 페이지 데이터
curl http://localhost:8080/api/main
```

## 🗄️ **7. 문제 해결**

### **연결 오류 시 확인사항**
1. **MySQL 서비스 실행 확인**
2. **포트 3306 사용 가능 확인**
3. **root 비밀번호 확인**
4. **데이터베이스 존재 확인**

### **로그 확인**
```bash
# 애플리케이션 로그에서 연결 상태 확인
tail -f logs/spring.log
```

## 🗄️ **8. 설정 변경**

### **다른 데이터베이스 사용 시**
`application-mysql.yml` 파일에서 다음 설정 변경:
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name
    username: your_username
    password: your_password
``` 