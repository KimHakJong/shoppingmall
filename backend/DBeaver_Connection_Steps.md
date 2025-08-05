# DBeaver MySQL 연결 설정 단계별 가이드

## 📸 **단계별 설정 방법**

### **1단계: DBeaver 실행 및 새 연결 생성**
```
1. DBeaver 실행
2. Database > New Database Connection 클릭
3. MySQL 선택 후 Next 클릭
```

### **2단계: 연결 정보 입력**
```
Connection Settings:
├── Server Host: localhost
├── Port: 3306
├── Database: shopping_mall
├── Username: root
└── Password: 1234
```

### **3단계: Driver Properties 설정 (중요!)**
```
Driver Properties 탭에서:
├── useSSL: false
├── serverTimezone: UTC
├── characterEncoding: UTF-8
└── allowPublicKeyRetrieval: true
```

### **4단계: 연결 테스트**
```
1. Test Connection 버튼 클릭
2. "Connected successfully" 메시지 확인
3. Finish 클릭
```

## 🔧 **상세 설정 옵션**

### **Connection Settings**
```
Main 탭:
├── Server Host: localhost
├── Port: 3306
├── Database: shopping_mall
├── Username: root
├── Password: 1234
└── Save password: ✓ 체크
```

### **Driver Properties**
```
Driver Properties 탭:
├── useSSL: false
├── serverTimezone: UTC
├── characterEncoding: UTF-8
├── allowPublicKeyRetrieval: true
├── autoReconnect: true
└── failOverReadOnly: false
```

### **Connection Pool**
```
Connection Pool 탭:
├── Initial pool size: 5
├── Max pool size: 20
├── Connection timeout: 30
└── Query timeout: 60
```

## 🚨 **자주 발생하는 오류 및 해결방법**

### **오류 1: "Public Key Retrieval is not allowed"**
```
해결방법:
Driver Properties에 추가:
allowPublicKeyRetrieval: true
```

### **오류 2: "Communications link failure"**
```
해결방법:
1. MySQL 서비스 실행 확인
2. 포트 3306 사용 가능 확인
3. 방화벽 설정 확인
```

### **오류 3: "Access denied for user 'root'"**
```
해결방법:
1. MySQL root 비밀번호 확인
2. application-mysql.yml과 일치하는지 확인
3. MySQL에서 권한 확인
```

## 📊 **연결 성공 후 확인사항**

### **1. 데이터베이스 생성**
```sql
-- SQL Editor에서 실행
CREATE DATABASE IF NOT EXISTS shopping_mall 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### **2. 테이블 생성 확인**
```
1. Navigator에서 shopping_mall 우클릭
2. Refresh 클릭
3. Tables 폴더 확인
```

### **3. 테스트 데이터 삽입**
```sql
-- 카테고리 데이터
INSERT INTO categories (name, code, created_at, updated_at) VALUES
('전자제품', 'electronics', NOW(), NOW()),
('의류', 'clothing', NOW(), NOW()),
('가전제품', 'appliances', NOW(), NOW()),
('스포츠용품', 'sports', NOW(), NOW());

-- 상품 데이터
INSERT INTO products (name, description, price, image_url, rating, category_id, created_at, updated_at) VALUES
('스마트폰', '최신 스마트폰', 800000, 'https://via.placeholder.com/200x200?text=Phone', 4.5, 1, NOW(), NOW()),
('노트북', '고성능 노트북', 1200000, 'https://via.placeholder.com/200x200?text=Laptop', 4.8, 1, NOW(), NOW());
```

## 🎯 **DBeaver 활용 팁**

### **1. SQL Editor 사용**
```
- Ctrl + Enter: 쿼리 실행
- Ctrl + Shift + Enter: 전체 스크립트 실행
- Ctrl + /: 주석 처리
```

### **2. 데이터 조회**
```sql
-- 카테고리 조회
SELECT * FROM categories;

-- 상품과 카테고리 함께 조회
SELECT 
    p.name as product_name,
    p.price,
    c.name as category_name
FROM products p
LEFT JOIN categories c ON p.category_id = c.id;
```

### **3. 테이블 구조 확인**
```
1. Tables > 테이블명 우클릭
2. View Table 선택
3. Columns, Keys, Indexes 탭 확인
```

이제 DBeaver에서 MySQL을 편리하게 관리할 수 있습니다! 🚀 