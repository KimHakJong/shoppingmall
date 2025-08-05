# MySQL 설치 및 환경설정 가이드

## 🗄️ **1. MySQL 8.0 설치**

### **Windows에서 MySQL 설치**

#### **1-1. MySQL Community Server 다운로드**
1. **MySQL 공식 사이트**: https://dev.mysql.com/downloads/mysql/
2. **MySQL Community Server 8.0.xx** 선택
3. **Windows (x86, 64-bit), ZIP Archive** 다운로드

#### **1-2. MySQL 설치**
```bash
# 1. 다운로드한 ZIP 파일 압축 해제
# 2. 원하는 경로에 압축 해제 (예: C:\mysql-8.0.xx)

# 3. 환경변수 설정
# 시스템 환경변수 > Path에 추가:
C:\mysql-8.0.xx\bin

# 4. MySQL 서비스 설치
cd C:\mysql-8.0.xx\bin
mysqld --install MySQL80

# 5. MySQL 서비스 시작
net start MySQL80
```

#### **1-3. MySQL 초기 설정**
```bash
# MySQL에 접속 (처음에는 비밀번호 없음)
mysql -u root

# root 비밀번호 설정 (application-mysql.yml과 일치하게)
ALTER USER 'root'@'localhost' IDENTIFIED BY '1234';

# MySQL 종료
exit
```

### **또는 MySQL Installer 사용 (권장)**

#### **1-1. MySQL Installer 다운로드**
1. **MySQL Installer**: https://dev.mysql.com/downloads/installer/
2. **Windows (x86, 32-bit), MSI Installer** 다운로드

#### **1-2. 설치 과정**
1. **Developer Default** 선택
2. **MySQL Server 8.0.xx** 선택
3. **Config Type**: Development Computer
4. **TCP/IP Port**: 3306 (기본값)
5. **Authentication Method**: Use Strong Password Encryption
6. **root 비밀번호**: `1234` (application-mysql.yml과 일치)
7. **Windows Service**: MySQL80

## 🗄️ **2. 데이터베이스 생성**

### **MySQL 명령어로 데이터베이스 생성**
```bash
# MySQL에 접속
mysql -u root -p
# 비밀번호 입력: 1234

# 데이터베이스 생성
CREATE DATABASE shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# 데이터베이스 확인
SHOW DATABASES;

# 사용할 데이터베이스 선택
USE shopping_mall;

# MySQL 종료
exit
```

### **또는 MySQL Workbench 사용**

#### **2-1. MySQL Workbench 설치**
1. **MySQL Workbench**: https://dev.mysql.com/downloads/workbench/
2. **Windows (x86, 64-bit), MSI Installer** 다운로드

#### **2-2. 연결 설정**
1. **MySQL Workbench 실행**
2. **Database > Connect to Database**
3. **Connection 설정**:
   - Hostname: `localhost`
   - Port: `3306`
   - Username: `root`
   - Password: `1234`

#### **2-3. 데이터베이스 생성**
```sql
-- SQL Editor에서 실행
CREATE DATABASE shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

## 🗄️ **3. 애플리케이션 설정**

### **MySQL 프로파일로 실행**
```bash
# 백엔드 디렉토리로 이동
cd backend

# MySQL 설정으로 스프링부트 실행
mvn spring-boot:run -Dspring.profiles.active=mysql
```

### **또는 application.yml 수정**
```yaml
# application.yml에 추가
spring:
  profiles:
    active: mysql
```

## 🗄️ **4. 테이블 자동 생성 확인**

### **JPA가 자동으로 테이블 생성**
- 애플리케이션 실행 시 다음 테이블들이 자동 생성됨:
  - `categories`: 카테고리 테이블
  - `products`: 상품 테이블

### **생성된 테이블 확인**
```sql
-- MySQL에 접속
mysql -u root -p
USE shopping_mall;

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
   ```bash
   # Windows 서비스에서 MySQL80 확인
   services.msc
   ```

2. **포트 3306 사용 가능 확인**
   ```bash
   # 포트 사용 확인
   netstat -an | findstr 3306
   ```

3. **root 비밀번호 확인**
   ```bash
   # MySQL 접속 테스트
   mysql -u root -p
   ```

4. **데이터베이스 존재 확인**
   ```sql
   SHOW DATABASES;
   USE shopping_mall;
   SHOW TABLES;
   ```

### **로그 확인**
```bash
# 애플리케이션 로그에서 연결 상태 확인
# 콘솔에서 다음 메시지 확인:
# "HikariCP - Starting..."
# "Hibernate - HHH000400: Using dialect: org.hibernate.dialect.MySQL8Dialect"
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

### **다른 포트 사용 시**
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3307/shopping_mall?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8
```

## 🗄️ **9. 성능 최적화**

### **MySQL 설정 최적화**
```sql
-- MySQL 설정 확인
SHOW VARIABLES LIKE 'max_connections';
SHOW VARIABLES LIKE 'innodb_buffer_pool_size';

-- 연결 풀 설정 확인
SHOW STATUS LIKE 'Threads_connected';
```

### **애플리케이션 설정 최적화**
```yaml
# application-mysql.yml에 추가
spring:
  datasource:
    hikari:
      maximum-pool-size: 10
      minimum-idle: 5
      connection-timeout: 30000
      idle-timeout: 600000
      max-lifetime: 1800000
``` 