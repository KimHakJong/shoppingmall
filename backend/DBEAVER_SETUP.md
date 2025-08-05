# DBeaver MySQL 연결 설정 가이드

## 🗄️ **1. DBeaver 설치**

### **DBeaver Community Edition 다운로드**
1. **DBeaver 공식 사이트**: https://dbeaver.io/download/
2. **DBeaver Community Edition** 선택
3. **Windows 64 bit** 다운로드 및 설치

## 🗄️ **2. MySQL 연결 설정**

### **2-1. 새 데이터베이스 연결 생성**
1. **DBeaver 실행**
2. **Database > New Database Connection** 클릭
3. **MySQL** 선택 후 **Next** 클릭

### **2-2. 연결 정보 입력**
```
Connection Settings:
├── Server Host: localhost
├── Port: 3306
├── Database: shopping_mall
├── Username: root
└── Password: 1234
```

### **2-3. 고급 설정 (선택사항)**
```
Driver Properties:
├── useSSL: false
├── serverTimezone: UTC
├── characterEncoding: UTF-8
└── allowPublicKeyRetrieval: true
```

### **2-4. 연결 테스트**
1. **Test Connection** 버튼 클릭
2. **Connected successfully** 메시지 확인
3. **Finish** 클릭

## 🗄️ **3. 데이터베이스 생성**

### **3-1. SQL Editor에서 데이터베이스 생성**
```sql
-- DBeaver SQL Editor에서 실행
CREATE DATABASE IF NOT EXISTS shopping_mall 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### **3-2. 데이터베이스 선택**
1. **Navigator** 패널에서 `shopping_mall` 우클릭
2. **Set Active** 선택

## 🗄️ **4. 테이블 자동 생성 확인**

### **4-1. 스프링부트 애플리케이션 실행**
```bash
cd backend
mvn spring-boot:run -Dspring.profiles.active=mysql
```

### **4-2. 테이블 생성 확인**
1. **DBeaver에서 새로고침** (F5)
2. **shopping_mall > Tables** 확인
3. 다음 테이블들이 생성됨:
   - `categories`: 카테고리 테이블
   - `products`: 상품 테이블

## 🗄️ **5. 테스트 데이터 삽입**

### **5-1. 카테고리 데이터 삽입**
```sql
-- DBeaver SQL Editor에서 실행
INSERT INTO categories (name, code, created_at, updated_at) VALUES
('전자제품', 'electronics', NOW(), NOW()),
('의류', 'clothing', NOW(), NOW()),
('가전제품', 'appliances', NOW(), NOW()),
('스포츠용품', 'sports', NOW(), NOW());
```

### **5-2. 상품 데이터 삽입**
```sql
-- DBeaver SQL Editor에서 실행
INSERT INTO products (name, description, price, image_url, rating, category_id, created_at, updated_at) VALUES
('스마트폰', '최신 스마트폰', 800000, 'https://via.placeholder.com/200x200?text=Phone', 4.5, 1, NOW(), NOW()),
('노트북', '고성능 노트북', 1200000, 'https://via.placeholder.com/200x200?text=Laptop', 4.8, 1, NOW(), NOW()),
('헤드폰', '무선 헤드폰', 150000, 'https://via.placeholder.com/200x200?text=Headphone', 4.3, 1, NOW(), NOW()),
('스마트워치', '스마트워치', 300000, 'https://via.placeholder.com/200x200?text=Watch', 4.6, 1, NOW(), NOW());
```

## 🗄️ **6. DBeaver 활용 팁**

### **6-1. 테이블 구조 확인**
1. **Tables > 테이블명** 우클릭
2. **View Table** 선택
3. **Columns, Keys, Indexes** 탭에서 구조 확인

### **6-2. 데이터 조회**
```sql
-- 카테고리 조회
SELECT * FROM categories;

-- 상품 조회 (카테고리 정보 포함)
SELECT 
    p.id,
    p.name,
    p.description,
    p.price,
    p.rating,
    c.name as category_name
FROM products p
LEFT JOIN categories c ON p.category_id = c.id;
```

### **6-3. MyBatis 쿼리 테스트**
```sql
-- 인기 상품 조회 (MyBatis 쿼리 테스트)
SELECT 
    p.id,
    p.name,
    p.description,
    p.price,
    p.image_url,
    p.rating,
    p.created_at,
    p.updated_at,
    c.id as category_id,
    c.name as category_name,
    c.code as category_code
FROM products p
LEFT JOIN categories c ON p.category_id = c.id
WHERE p.rating >= 4.0
ORDER BY p.rating DESC, p.created_at DESC
LIMIT 8;
```

## 🗄️ **7. 문제 해결**

### **7-1. 연결 오류 시 확인사항**
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

### **7-2. DBeaver 설정 문제**
1. **Driver Properties 확인**
   ```
   useSSL=false
   serverTimezone=UTC
   characterEncoding=UTF-8
   allowPublicKeyRetrieval=true
   ```

2. **Connection Timeout 설정**
   ```
   Connection timeout: 30
   Query timeout: 60
   ```

## 🗄️ **8. 고급 기능**

### **8-1. 데이터 내보내기/가져오기**
1. **테이블 우클릭 > Export Data**
2. **CSV, Excel, JSON** 등 다양한 형식 지원

### **8-2. ER 다이어그램 생성**
1. **Database > Generate ER Diagram**
2. **테이블 관계 시각화**

### **8-3. SQL 히스토리**
1. **SQL Editor > History**
2. **실행한 쿼리 기록 확인**

## 🗄️ **9. 성능 모니터링**

### **9-1. 쿼리 실행 계획**
```sql
-- EXPLAIN으로 쿼리 성능 분석
EXPLAIN SELECT * FROM products WHERE category_id = 1;
```

### **9-2. 인덱스 확인**
```sql
-- 테이블 인덱스 확인
SHOW INDEX FROM products;
```

### **9-3. 테이블 통계**
```sql
-- 테이블 크기 및 행 수 확인
SELECT 
    TABLE_NAME,
    TABLE_ROWS,
    DATA_LENGTH,
    INDEX_LENGTH
FROM information_schema.TABLES 
WHERE TABLE_SCHEMA = 'shopping_mall';
```

## 🗄️ **10. 백업 및 복원**

### **10-1. 데이터베이스 백업**
```sql
-- DBeaver에서 백업 실행
-- Database > Tools > Backup Database
```

### **10-2. SQL 스크립트 생성**
```sql
-- 테이블 구조 및 데이터를 SQL로 내보내기
-- Tables > 테이블명 우클릭 > Generate SQL > DDL
```

이제 DBeaver에서 MySQL을 편리하게 관리할 수 있습니다! 🚀 