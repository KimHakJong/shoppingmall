-- shopping_mall.categories definition

CREATE TABLE `categories` (
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
  `admin_yn` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id` bigint NOT NULL,
  `code` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `created_at` datetime(6) DEFAULT NULL,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `updated_at` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`menu_id`),
  KEY `parent_menu_id` (`parent_menu_id`),
  CONSTRAINT `categories_ibfk_1` FOREIGN KEY (`parent_menu_id`) REFERENCES `categories` (`menu_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  `updated_tsp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '최종변경시간',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='회원';