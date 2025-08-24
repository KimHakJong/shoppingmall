-- HPIX 쇼핑몰 데이터베이스 테이블 생성 스크립트

-- 데이터베이스 생성 (필요시)
-- CREATE DATABASE IF NOT EXISTS shopping_mall CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- USE shopping_mall;

-- 메뉴 테이블
CREATE TABLE `menus` (
  `menu_id` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '메뉴ID',
  `menu_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '메뉴명',
  `parent_menu_id` varchar(10) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '상위메뉴ID',
  `menu_depth` int NOT NULL COMMENT '메뉴깊이',
  `menu_order` int NOT NULL COMMENT '메뉴순서',
  `usg_yn` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'Y' COMMENT '사용여부',
  `created_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '최초생성자ID',
  `created_tsp` timestamp NOT NULL COMMENT '최초생성시간',
  `updated_id` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '최종변경자ID',
  `updated_tsp` timestamp NOT NULL COMMENT '최종변경시간',
  `admin_yn` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '관리자여부',
  `id` bigint NOT NULL COMMENT 'ID',
  `code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '코드',
  `created_at` datetime(6) DEFAULT NULL COMMENT '생성시간',
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '이름',
  `updated_at` datetime(6) DEFAULT NULL COMMENT '수정시간',
  PRIMARY KEY (`menu_id`),
  KEY `parent_menu_id` (`parent_menu_id`),
  CONSTRAINT `menus_ibfk_1` FOREIGN KEY (`parent_menu_id`) REFERENCES `menus` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='메뉴';

-- 회원 테이블
CREATE TABLE `users` (
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '아이디',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '이름',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '비밀번호',
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '이메일',
  `cell_tphn` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '휴대폰번호',
  `dtbr` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '생년월일',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '성별',
  `zip` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '우편번호',
  `bscs_addr` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '기본 주소',
  `dtl_addr` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '상세 주소',
  `user_role` varchar(10) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '권한',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '최종변경시간',
  `refresh_token` varchar(1024) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JWT 리프레시 토큰 (토큰 재발급 요청 시 사용)',
  `refresh_token_expiry` datetime DEFAULT NULL COMMENT '리프레시 토큰 만료일시',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원';

-- 샘플 메뉴 데이터 삽입
INSERT INTO `menus` (`menu_id`, `menu_name`, `parent_menu_id`, `menu_depth`, `menu_order`, `usg_yn`, `created_id`, `created_tsp`, `updated_id`, `updated_tsp`, `admin_yn`, `id`, `code`, `created_at`, `name`, `updated_at`) VALUES
('M001', 'NEW', NULL, 1, 1, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 1, 'NEW', NOW(), 'NEW', NOW()),
('M002', 'INSTOCK', NULL, 1, 2, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 2, 'INSTOCK', NOW(), 'INSTOCK', NOW()),
('M003', 'FURNITURE', NULL, 1, 3, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 3, 'FURNITURE', NOW(), 'FURNITURE', NOW()),
('M004', 'LIGHTING', NULL, 1, 4, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 4, 'LIGHTING', NOW(), 'LIGHTING', NOW()),
('M005', 'DECO', NULL, 1, 5, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 5, 'DECO', NOW(), 'DECO', NOW()),
('M006', 'TABLE WARE', NULL, 1, 6, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 6, 'TABLE_WARE', NOW(), 'TABLE WARE', NOW()),
('M007', 'CRAFT', NULL, 1, 7, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 7, 'CRAFT', NOW(), 'CRAFT', NOW()),
('M008', 'LIVING ITEM', NULL, 1, 8, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 8, 'LIVING_ITEM', NOW(), 'LIVING ITEM', NOW()),
('M009', 'PROMOTION', NULL, 1, 9, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 9, 'PROMOTION', NOW(), 'PROMOTION', NOW()),
('M010', 'OUTLET', NULL, 1, 10, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 10, 'OUTLET', NOW(), 'OUTLET', NOW()),
('M011', 'BRAND', NULL, 1, 11, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 11, 'BRAND', NOW(), 'BRAND', NOW()),
('M012', 'AGO LIGHTING', 'M004', 2, 1, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 12, 'AGO_LIGHTING', NOW(), 'AGO LIGHTING', NOW()),
('M013', 'BETTER ELEMENTS STUDIO', 'M004', 2, 2, 'Y', 'admin', NOW(), 'admin', NOW(), 'N', 13, 'BETTER_ELEMENTS_STUDIO', NOW(), 'BETTER ELEMENTS STUDIO', NOW());

-- 샘플 사용자 데이터 삽입 (비밀번호: 1234)
INSERT INTO `users` (`user_id`, `user_name`, `user_password`, `user_email`, `user_role`, `created_tsp`, `updated_tsp`) VALUES
('admin', '관리자', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'admin@hpix.com', 'ADMIN', NOW(), NOW()),
('test', '테스트', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'test@hpix.com', 'USER', NOW(), NOW());