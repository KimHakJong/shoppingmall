-- shopping_mall 데이터베이스 사용
USE shopping_mall;

-- 기존 테이블이 있다면 삭제 (주의: 데이터가 모두 삭제됩니다)
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS categories;

-- 카테고리 테이블 생성
CREATE TABLE categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    code VARCHAR(50) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 상품 테이블 생성
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock_quantity INT DEFAULT 0,
    category_id BIGINT,
    rating DECIMAL(3,2) DEFAULT 0.00,
    image_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(id)
);

-- 테스트 데이터 삽입
INSERT INTO categories (name, code) VALUES 
('전자제품', 'ELECTRONICS'),
('의류', 'CLOTHING'),
('도서', 'BOOKS'),
('스포츠용품', 'SPORTS'),
('식품', 'FOOD');

INSERT INTO products (name, description, price, stock_quantity, category_id, rating, image_url) VALUES 
('스마트폰 A', '최신 스마트폰', 800000.00, 50, 1, 4.5, '/images/phone.jpg'),
('노트북 B', '고성능 노트북', 1200000.00, 30, 1, 4.8, '/images/laptop.jpg'),
('티셔츠 C', '편안한 티셔츠', 25000.00, 100, 2, 4.2, '/images/tshirt.jpg'),
('청바지 D', '스타일리시한 청바지', 45000.00, 80, 2, 4.3, '/images/jeans.jpg'),
('프로그래밍 책', '자바 프로그래밍', 35000.00, 25, 3, 4.6, '/images/book.jpg'),
('축구공', '프로 축구공', 15000.00, 60, 4, 4.4, '/images/soccer.jpg'),
('과일 세트', '신선한 과일', 20000.00, 40, 5, 4.1, '/images/fruits.jpg');

-- 테이블 생성 확인
SELECT 'Categories table:' as table_name, COUNT(*) as count FROM categories
UNION ALL
SELECT 'Products table:' as table_name, COUNT(*) as count FROM products; 