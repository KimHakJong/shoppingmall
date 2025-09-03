package com.shopping.backend.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * 토큰을 AES 방식으로 암호화/복호화하는 유틸리티 클래스
 */
public class TokenEncryptor {
    // 사용할 암호화 알고리즘
    private static final String ALGORITHM = "AES";
    // 32바이트(256bit) 고정 키 (실제 서비스에서는 안전하게 관리 필요)
    private static final byte[] KEY = "mySuperSecretKey1234567890123456".getBytes(); // 32바이트 (256bit)

    // 암호화/복호화에 사용할 SecretKeySpec 객체 생성
    private static SecretKeySpec secretKey = new SecretKeySpec(KEY, ALGORITHM);

    /**
     * 토큰을 AES로 암호화하여 Base64 문자열로 반환
     * @param token 암호화할 평문 토큰
     * @return 암호화된 토큰(Base64 인코딩)
     * @throws Exception 암호화 과정에서 발생하는 예외
     */
    public static String encrypt(String token) throws Exception {
        // Cipher 객체를 AES 알고리즘으로 초기화
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 암호화 모드로 설정
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        // 토큰을 바이트 배열로 변환 후 암호화
        byte[] encrypted = cipher.doFinal(token.getBytes());

        // 암호화된 바이트 배열을 Base64 문자열로 인코딩하여 반환
        return Base64.getEncoder().encodeToString(encrypted);        
    }

    /**
     * 암호화된 토큰(Base64 문자열)을 복호화하여 원래의 토큰 문자열로 반환
     * @param encryptedToken 암호화된 토큰(Base64 인코딩)
     * @return 복호화된 평문 토큰
     * @throws Exception 복호화 과정에서 발생하는 예외
     */
    public static String decrypt(String encryptedToken) throws Exception {
        // Cipher 객체를 AES 알고리즘으로 초기화
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        // 복호화 모드로 설정
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        // Base64로 인코딩된 암호문을 디코딩하여 바이트 배열로 변환
        byte[] decoded = Base64.getDecoder().decode(encryptedToken);
        
        // 복호화하여 원래의 토큰 문자열로 변환 후 반환
        return new String(cipher.doFinal(decoded));
    }    
}