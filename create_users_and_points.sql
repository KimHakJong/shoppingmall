-- 쇼핑몰 회원 및 포인트 이력 테이블 생성

-- 1. users 회원 테이블
CREATE TABLE users (
    user_id VARCHAR(50) PRIMARY KEY COMMENT '아이디',
    user_name VARCHAR(100) NOT NULL COMMENT '이름',
    user_password VARCHAR(255) NOT NULL COMMENT '비밀번호',
    user_email VARCHAR(100) NOT NULL UNIQUE COMMENT '이메일',
    cell_tphn VARCHAR(20) COMMENT '휴대폰번호',
    dtbr DATE COMMENT '생년월일',
    gender ENUM('MALE', 'FEMALE', 'OTHER') COMMENT '성별',
    zip VARCHAR(10) COMMENT '우편번호',
    bscs_addr VARCHAR(200) COMMENT '기본 주소',
    dtl_addr VARCHAR(200) COMMENT '상세 주소',
    created_tsp TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
    updated_tsp TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종변경시간'
) COMMENT '회원 정보 테이블';

-- 2. point_history 포인트 이력 테이블
CREATE TABLE point_history (
    user_id VARCHAR(50) NOT NULL COMMENT '아이디',
    tran_dt DATE NOT NULL COMMENT '처리일자',
    tran_sn INT NOT NULL COMMENT '처리순번',
    type_cd ENUM('EARN', 'USE', 'EXPIRE', 'REFUND') NOT NULL COMMENT '유형코드',
    tran_pt INT NOT NULL COMMENT '처리포인트',
    rslt_pt INT NOT NULL COMMENT '결과포인트',
    created_tsp TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '생성시간',
    
    -- 복합 기본키 설정
    PRIMARY KEY (user_id, tran_dt, tran_sn),
    
    -- 외래키 설정
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
) COMMENT '포인트 이력 테이블';

-- 3. 인덱스 생성
-- users 테이블 인덱스
CREATE INDEX idx_users_email ON users(user_email);
CREATE INDEX idx_users_phone ON users(cell_tphn);
CREATE INDEX idx_users_created ON users(created_tsp);

-- point_history 테이블 인덱스
CREATE INDEX idx_point_history_user_date ON point_history(user_id, tran_dt);
CREATE INDEX idx_point_history_type ON point_history(type_cd);
CREATE INDEX idx_point_history_created ON point_history(created_tsp);

-- 4. 테이블 생성 확인
SELECT 
    'users' as table_name, 
    COUNT(*) as record_count 
FROM information_schema.tables 
WHERE table_schema = 'shopping_mall' AND table_name = 'users'
UNION ALL
SELECT 
    'point_history' as table_name, 
    COUNT(*) as record_count 
FROM information_schema.tables 
WHERE table_schema = 'shopping_mall' AND table_name = 'point_history'; 