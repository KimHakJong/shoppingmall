-- 기존 users 테이블에 refresh_token 관련 컬럼 추가
USE shopping_mall;

-- refresh_token 컬럼 추가
ALTER TABLE users 
ADD COLUMN refresh_token VARCHAR(500) DEFAULT NULL COMMENT '리프레시 토큰';

-- refresh_token_expiry 컬럼 추가
ALTER TABLE users 
ADD COLUMN refresh_token_expiry DATETIME DEFAULT NULL COMMENT '리프레시 토큰 만료시간';

-- updated_tsp 컬럼에 ON UPDATE CURRENT_TIMESTAMP 추가
ALTER TABLE users 
MODIFY COLUMN updated_tsp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종변경시간';

-- 테이블 구조 확인
DESCRIBE users;
