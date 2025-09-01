-- OAuth 관련 테이블 생성 스크립트
-- 네이버, 카카오, 구글 등 OAuth 로그인을 위한 테이블 구조

-- 1. 기존 users 테이블에서 OAuth 관련 컬럼 제거
ALTER TABLE users 
DROP COLUMN IF EXISTS naver_id,
DROP COLUMN IF EXISTS user_profile_image;

-- 2. OAuth 사용자 테이블 생성
CREATE TABLE oauth_users (
    oauth_user_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT 'OAuth 사용자 ID',
    user_id VARCHAR(20) NOT NULL COMMENT '연관된 Users 테이블의 user_id',
    provider VARCHAR(20) NOT NULL COMMENT 'OAuth 제공자 (NAVER, KAKAO, GOOGLE 등)',
    provider_user_id VARCHAR(100) NOT NULL COMMENT 'OAuth 제공자에서 제공하는 고유 ID',
    provider_email VARCHAR(100) COMMENT 'OAuth 제공자에서 제공하는 이메일',
    provider_name VARCHAR(100) COMMENT 'OAuth 제공자에서 제공하는 이름',
    provider_nickname VARCHAR(100) COMMENT 'OAuth 제공자에서 제공하는 닉네임',
    provider_profile_image VARCHAR(500) COMMENT 'OAuth 제공자에서 제공하는 프로필 이미지 URL',
    provider_gender VARCHAR(10) COMMENT 'OAuth 제공자에서 제공하는 성별',
    provider_birth_year VARCHAR(4) COMMENT 'OAuth 제공자에서 제공하는 생년월일',
    provider_birthday VARCHAR(10) COMMENT 'OAuth 제공자에서 제공하는 생일',
    provider_age_range VARCHAR(10) COMMENT 'OAuth 제공자에서 제공하는 연령대',
    provider_mobile VARCHAR(20) COMMENT 'OAuth 제공자에서 제공하는 휴대폰 번호',
    access_token VARCHAR(1000) COMMENT 'OAuth 액세스 토큰 (암호화하여 저장)',
    refresh_token VARCHAR(1000) COMMENT 'OAuth 리프레시 토큰 (암호화하여 저장)',
    access_token_expires_at DATETIME COMMENT '액세스 토큰 만료 시간',
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초 생성 시간',
    updated_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종 수정 시간',
    is_active VARCHAR(1) NOT NULL DEFAULT 'Y' COMMENT '사용 여부 (Y/N)',
    
    -- 인덱스 생성
    INDEX idx_oauth_users_user_id (user_id),
    INDEX idx_oauth_users_provider (provider),
    INDEX idx_oauth_users_provider_user_id (provider_user_id),
    INDEX idx_oauth_users_provider_provider_user_id (provider, provider_user_id),
    INDEX idx_oauth_users_user_id_provider (user_id, provider),
    INDEX idx_oauth_users_is_active (is_active),
    INDEX idx_oauth_users_access_token_expires_at (access_token_expires_at),
    
    -- 외래키 제약조건
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    
    -- 유니크 제약조건 (한 제공자에서 같은 사용자 ID는 중복 불가)
    UNIQUE KEY uk_oauth_users_provider_provider_user_id (provider, provider_user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth 사용자 정보 테이블';

-- 3. 기존 users 테이블에 user_type, usg_yn 컬럼 추가 (없는 경우)
ALTER TABLE users 
ADD COLUMN IF NOT EXISTS user_type VARCHAR(20) COMMENT '사용자 타입 (EMAIL, NAVER, KAKAO 등)',
ADD COLUMN IF NOT EXISTS usg_yn VARCHAR(1) DEFAULT 'Y' COMMENT '사용 여부 (Y/N)';

-- 4. 기존 사용자들의 user_type을 EMAIL로 설정
UPDATE users SET user_type = 'EMAIL' WHERE user_type IS NULL;

-- 5. 샘플 데이터 삽입 (테스트용)
-- INSERT INTO oauth_users (user_id, provider, provider_user_id, provider_email, provider_name, provider_nickname, provider_profile_image, is_active) 
-- VALUES ('USER_1234567890_abc12345', 'NAVER', 'naver_user_123', 'test@naver.com', '테스트 사용자', '테스트닉네임', 'https://example.com/profile.jpg', 'Y');

-- 6. 테이블 생성 확인을 위한 뷰 생성
CREATE OR REPLACE VIEW oauth_user_summary AS
SELECT 
    u.user_id,
    u.user_name,
    u.user_email,
    u.user_type,
    u.usg_yn,
    COUNT(o.oauth_user_id) as oauth_account_count,
    GROUP_CONCAT(o.provider) as connected_providers
FROM users u
LEFT JOIN oauth_users o ON u.user_id = o.user_id AND o.is_active = 'Y'
GROUP BY u.user_id, u.user_name, u.user_email, u.user_type, u.usg_yn;

-- 7. OAuth 제공자별 통계 뷰 생성
CREATE OR REPLACE VIEW oauth_provider_stats AS
SELECT 
    provider,
    COUNT(*) as total_users,
    COUNT(CASE WHEN is_active = 'Y' THEN 1 END) as active_users,
    COUNT(CASE WHEN is_active = 'N' THEN 1 END) as inactive_users,
    MIN(created_at) as first_registration,
    MAX(created_at) as last_registration
FROM oauth_users
GROUP BY provider;

-- 8. 만료된 토큰 조회를 위한 뷰 생성
CREATE OR REPLACE VIEW expired_oauth_tokens AS
SELECT 
    o.oauth_user_id,
    o.user_id,
    o.provider,
    o.provider_user_id,
    o.access_token_expires_at,
    o.updated_at
FROM oauth_users o
WHERE o.access_token_expires_at < NOW() 
  AND o.is_active = 'Y'
  AND o.access_token IS NOT NULL;

-- 테이블 생성 완료 메시지
SELECT 'OAuth 관련 테이블 생성이 완료되었습니다.' as message;
