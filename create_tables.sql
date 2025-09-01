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
  `admin_yn` char(1) COLLATE utf8mb4_unicode_ci DEFAULT null COMMENT '관리자 여부',
  PRIMARY KEY (`menu_id`),
  KEY `parent_menu_id` (`parent_menu_id`),
  CONSTRAINT `menus_ibfk_1` FOREIGN KEY (`parent_menu_id`) REFERENCES `menus` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;


-- 회원 테이블
CREATE TABLE `users` (
  `user_id` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '아이디',
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '이름',
  `user_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '비밀번호',
  `user_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '회원상태',
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '이메일',
  `cell_tphn` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '휴대폰번호',
  `dtbr` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '생년월일',
  `gender` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '성별',
  `zip` varchar(6) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '우편번호',
  `bscs_addr` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '기본 주소',
  `dtl_addr` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '상세 주소',
  `user_role` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '권한',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  `refresh_token` varchar(1024) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT 'JWT 리프레시 토큰 (토큰 재발급 요청 시 사용)',
  `refresh_token_expiry` datetime DEFAULT NULL COMMENT '리프레시 토큰 만료일시',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원';

-- 상품 테이블
CREATE TABLE `product` (
  `prod_id` bigint NOT NULL AUTO_INCREMENT COMMENT '상품ID',
  `prod_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '상품명',
  `menu_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '상품메뉴',
  `prod_description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci COMMENT '상품설명',
  `prod_price` int NOT NULL COMMENT '상품가격',
  `prod_status` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '판매상태',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  PRIMARY KEY (`prod_id`),
  KEY `idx_menu_id` (`menu_id`),
  KEY `idx_prod_status` (`prod_status`),
  KEY `idx_created_tsp` (`created_tsp`),
  CONSTRAINT `fk_menu_id` FOREIGN KEY (`menu_id`) REFERENCES `menus` (`menu_id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품';

-- 상품 옵션 테이블
CREATE TABLE `product_option` (
  `option_id` bigint NOT NULL AUTO_INCREMENT COMMENT '옵션ID',
  `prod_id` bigint NOT NULL COMMENT '상품ID',
  `option_name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '옵션명',
  `color` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '색상',
  `size` varchar(20) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '사이즈',
  `stock` int NOT NULL DEFAULT '0' COMMENT '재고수량',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  PRIMARY KEY (`option_id`),
  KEY `idx_prod_id` (`prod_id`),
  KEY `idx_stock` (`stock`),
  CONSTRAINT `fk_product_option_prod` FOREIGN KEY (`prod_id`) REFERENCES `product` (`prod_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품옵션';

-- 상품 이미지 테이블
CREATE TABLE `product_image` (
  `image_id` bigint NOT NULL AUTO_INCREMENT COMMENT '이미지ID',
  `prod_id` bigint NOT NULL COMMENT '상품ID',
  `image_url` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '이미지 경로',
  `thumbnail_yn` char(1) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'N' COMMENT '대표이미지여부',
  `image_sn` int NOT NULL DEFAULT '0' COMMENT '순서',
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  PRIMARY KEY (`image_id`),
  KEY `idx_prod_id` (`prod_id`),
  KEY `idx_thumbnail_yn` (`thumbnail_yn`),
  KEY `idx_image_sn` (`image_sn`),
  CONSTRAINT `fk_product_image_prod` FOREIGN KEY (`prod_id`) REFERENCES `product` (`prod_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='상품이미지';

--공통코드 테이블
CREATE TABLE common_code (
    `code_group` VARCHAR(30) NOT NULL,
    `code_value` VARCHAR(10) NOT NULL,
    `code_name` VARCHAR(50) NOT NULL,
    `usg_yn` CHAR(1),
  `created_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최초생성시간',
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
    PRIMARY KEY (code_group, code_value)
);
-- 
-- 샘플 메뉴 데이터 삽입
INSERT INTO `menus` (`menu_id`, `menu_name`, `parent_menu_id`, `menu_depth`, `menu_order`, `usg_yn`, `created_id`, `created_tsp`, `updated_id`, `updated_tsp`, `admin_yn`)
values
('MENU_07', '카테고리', NULL, 1, 1, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_01', '아우터', 'MENU_07', 2, 1, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_02', '상의', 'MENU_07', 2, 2, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_03', '셔츠', 'MENU_07', 2, 3, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_04', '바지', 'MENU_07', 2, 4, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_05', '신발', 'MENU_07', 2, 5, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL),
('MENU_06', '시계', 'MENU_07', 2, 6, 'Y', '1234', '2025-08-24 16:00:00', '1234', '2025-08-24 16:00:00', NULL);

-- 샘플 사용자 데이터 삽입 (비밀번호: 1234)
INSERT INTO `users` (`user_id`, `user_name`, `user_password`, `user_email`, `user_role`, `created_tsp`, `updated_tsp`) VALUES
('admin', '관리자', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'admin@hpix.com', 'ADMIN', NOW(), NOW()),
('test', '테스트', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDa', 'test@hpix.com', 'USER', NOW(), NOW());

-- 샘플 상품 데이터 삽입
INSERT INTO `product` (`prod_name`, `menu_id`, `prod_description`, `prod_price`, `prod_status`) VALUES
('모던 테이블 램프', 'outer', '심플하고 모던한 디자인의 테이블 램프입니다. 다양한 인테리어에 잘 어울립니다.', 89000, '1'),
('우드 책상', 'top', '천연 우드로 제작된 프리미엄 책상입니다. 내구성이 뛰어나고 세련된 디자인을 자랑합니다.', 250000, '1'),
('세라믹 화분 세트', 'outer', '3개 세트로 구성된 세라믹 화분입니다. 실내 인테리어에 포인트를 줄 수 있습니다.', 45000, '1');

-- 샘플 상품 옵션 데이터 삽입
INSERT INTO `product_option` (`prod_id`, `option_name`, `color`, `size`, `stock`) VALUES
(1, '블랙', 'BLACK', NULL, 15),
(1, '화이트', 'WHITE', NULL, 20),
(1, '네이비', 'NAVY', NULL, 8),
(2, '월넛', 'WALNUT', '1200x600', 5),
(2, '오크', 'OAK', '1200x600', 3),
(2, '월넛', 'WALNUT', '1400x700', 2),
(3, '베이지', 'BEIGE', 'S', 25),
(3, '베이지', 'BEIGE', 'M', 18),
(3, '베이지', 'BEIGE', 'L', 12);

-- 샘플 상품 이미지 데이터 삽입
INSERT INTO `product_image` (`prod_id`, `image_url`, `thumbnail_yn`, `image_sn`) VALUES
(1, '/images/products/lamp_black_main.jpg', 'Y', 1),
(1, '/images/products/lamp_black_detail1.jpg', 'N', 2),
(1, '/images/products/lamp_black_detail2.jpg', 'N', 3),
(1, '/images/products/lamp_white_main.jpg', 'N', 4),
(2, '/images/products/desk_walnut_main.jpg', 'Y', 1),
(2, '/images/products/desk_walnut_detail1.jpg', 'N', 2),
(2, '/images/products/desk_oak_main.jpg', 'N', 3),
(3, '/images/products/pot_set_beige_main.jpg', 'Y', 1),
(3, '/images/products/pot_set_beige_detail1.jpg', 'N', 2);

--공통 코드 데이터 삽입
INSERT INTO common_code 
(code_group, code_value, code_name, usg_yn, created_tsp, updated_tsp) 
VALUES
('user_status', '0', '정상', 'Y', '2025-08-31 10:56:20', '2025-08-31 10:56:20'),
('user_status', '1', '휴먼', 'Y', '2025-08-31 10:56:20', '2025-08-31 10:56:20'),
('user_status', '2', '탈퇴', 'Y', '2025-08-31 10:56:20', '2025-08-31 10:56:20'),
('user_status', '3', '정지', 'Y', '2025-08-31 10:56:20', '2025-08-31 10:56:20'),
('prod_status', '0', '판매중', 'Y', '2025-08-31 10:58:01', '2025-08-31 10:58:01'),
('prod_status', '1', '품절', 'Y', '2025-08-31 10:58:01', '2025-08-31 10:58:01'),
('prod_status', '2', '판매중지', 'Y', '2025-08-31 10:58:01', '2025-08-31 10:58:01');