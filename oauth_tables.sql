-- OAuth 관련 테이블 생성 스크립트
-- 네이버, 카카오, 구글 등 OAuth 로그인을 위한 테이블 구조
CREATE TABLE `auth_users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'OAuth 회원 고유 식별자 (PK)',
  `user_id` varchar(20) NOT null COMMENT '아이디',
  `provider` varchar(20) NOT NULL COMMENT 'OAuth 로그인 구분',
  `provider_user_id` varchar(255) NOT NULL COMMENT '공급자 고유 ID',
  `refresh_token` varchar(4096) DEFAULT null COMMENT 'OAuth 리프레시 토큰',
  `refresh_token_expiry` datetime DEFAULT NULL COMMENT '리프레시 토큰 만료일시',
  `scope` varchar(512) DEFAULT null COMMENT '발급 범위',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_provider_user` (`provider`, `provider_user_id`),
  KEY `idx_user_provider` (`user_id`, `provider`),
  CONSTRAINT `fk_oauth_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='OAuth 회원';
