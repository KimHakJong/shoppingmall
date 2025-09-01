package com.shopping.backend.constant;

/**
 * API 에러 코드 상수 정의
 * 실무에서 사용하는 표준적인 에러 코드 체계
 */
public class ErrorCode {
    
    // ===== 공통 에러 코드 =====
    public static final String SUCCESS = "SUCCESS";
    public static final String UNKNOWN_ERROR = "UNKNOWN_ERROR";
    public static final String INVALID_REQUEST = "INVALID_REQUEST";
    public static final String UNAUTHORIZED = "UNAUTHORIZED";
    public static final String FORBIDDEN = "FORBIDDEN";
    public static final String NOT_FOUND = "NOT_FOUND";
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";
    
    // ===== 사용자 관련 에러 코드 =====
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";
    public static final String INVALID_USER_ID = "INVALID_USER_ID";
    public static final String INVALID_USER_NAME = "INVALID_USER_NAME";
    public static final String INVALID_PASSWORD = "INVALID_PASSWORD";
    public static final String DUPLICATE_USER_ID = "DUPLICATE_USER_ID";
    public static final String DUPLICATE_USER_EMAIL = "DUPLICATE_USER_EMAIL";
    public static final String JOIN_ERROR = "JOIN_ERROR";
    public static final String LOGOUT_ERROR = "LOGOUT_ERROR";
    
    // ===== 인증/토큰 관련 에러 코드 =====
    public static final String INVALID_ACCESS_TOKEN = "INVALID_ACCESS_TOKEN";
    public static final String INVALID_REFRESH_TOKEN = "INVALID_REFRESH_TOKEN";
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";
    public static final String TOKEN_SAVE_ERROR = "TOKEN_SAVE_ERROR";
    public static final String TOKEN_REFRESH_ERROR = "TOKEN_REFRESH_ERROR";
    
    // ===== 메뉴 관련 에러 코드 =====
    public static final String MENU_NOT_FOUND = "MENU_NOT_FOUND";
    public static final String INVALID_MENU_ID = "INVALID_MENU_ID";
    public static final String MENU_SEARCH_ERROR = "MENU_SEARCH_ERROR";
    public static final String MENU_PRODUCTS_ERROR = "MENU_PRODUCTS_ERROR";
    
    // ===== 상품 관련 에러 코드 =====
    public static final String PRODUCT_NOT_FOUND = "PRODUCT_NOT_FOUND";
    public static final String INVALID_PRODUCT_ID = "INVALID_PRODUCT_ID";
    public static final String PRODUCT_SEARCH_ERROR = "PRODUCT_SEARCH_ERROR";
    public static final String PRODUCT_OUT_OF_STOCK = "PRODUCT_OUT_OF_STOCK";
    
    // ===== 카테고리 관련 에러 코드 =====
    public static final String CATEGORY_NOT_FOUND = "CATEGORY_NOT_FOUND";
    public static final String INVALID_CATEGORY_ID = "INVALID_CATEGORY_ID";
    
    // ===== 데이터베이스 관련 에러 코드 =====
    public static final String DATABASE_ERROR = "DATABASE_ERROR";
    public static final String DATA_INTEGRITY_ERROR = "DATA_INTEGRITY_ERROR";
    
    // ===== 파일 업로드 관련 에러 코드 =====
    public static final String FILE_UPLOAD_ERROR = "FILE_UPLOAD_ERROR";
    public static final String INVALID_FILE_TYPE = "INVALID_FILE_TYPE";
    public static final String FILE_SIZE_EXCEEDED = "FILE_SIZE_EXCEEDED";
    
    // ===== 외부 API 관련 에러 코드 =====
    public static final String EXTERNAL_API_ERROR = "EXTERNAL_API_ERROR";
    public static final String EXTERNAL_API_TIMEOUT = "EXTERNAL_API_TIMEOUT";
    
    // ===== 비즈니스 로직 관련 에러 코드 =====
    public static final String BUSINESS_LOGIC_ERROR = "BUSINESS_LOGIC_ERROR";
    public static final String INSUFFICIENT_PERMISSIONS = "INSUFFICIENT_PERMISSIONS";
    public static final String OPERATION_NOT_ALLOWED = "OPERATION_NOT_ALLOWED";
}
