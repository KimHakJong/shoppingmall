package com.shopping.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;

/**
 * 네이버 사용자 정보 API 응답 DTO
 * 네이버 사용자 정보 조회 API의 응답을 매핑하는 객체
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NaverUserInfoResponse {
    
    /**
     * API 응답 결과 코드
     * "00"이면 성공, 그 외는 실패
     */
    @JsonProperty("resultcode")
    private String resultCode;
    
    /**
     * API 응답 메시지
     */
    @JsonProperty("message")
    private String message;
    
    /**
     * 사용자 정보 객체
     */
    @JsonProperty("response")
    private NaverUserInfo userInfo;
    
    /**
     * 네이버 사용자 정보 내부 클래스
     */
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class NaverUserInfo {
        
        /**
         * 네이버 사용자 고유 ID
         * 네이버에서 제공하는 고유한 사용자 식별자
         */
        @JsonProperty("id")
        private String id;
        
        /**
         * 사용자 이메일
         * 네이버 계정에 등록된 이메일 주소
         */
        @JsonProperty("email")
        private String email;
        
        /**
         * 사용자 이름
         * 네이버에서 설정한 사용자 이름
         */
        @JsonProperty("name")
        private String name;
        
        /**
         * 사용자 닉네임
         * 네이버에서 설정한 사용자 닉네임
         */
        @JsonProperty("nickname")
        private String nickname;
        
        /**
         * 사용자 성별
         * "M" (남성), "F" (여성), "U" (미설정)
         */
        @JsonProperty("gender")
        private String gender;
        
        /**
         * 사용자 나이
         * 연령대 정보 (예: "20-29")
         */
        @JsonProperty("age")
        private String age;
        
        /**
         * 사용자 생일
         * 생일 정보 (예: "12-25")
         */
        @JsonProperty("birthday")
        private String birthday;
        
        /**
         * 사용자 프로필 이미지 URL
         * 네이버 프로필에 등록된 이미지의 URL
         */
        @JsonProperty("profile_image")
        private String profileImage;
        
        /**
         * 사용자 연령대
         * 연령대 정보 (예: "20-29")
         */
        @JsonProperty("age_range")
        private String ageRange;
        
        /**
         * 사용자 생년월일
         * 생년월일 정보 (예: "1990-12-25")
         */
        @JsonProperty("birthyear")
        private String birthYear;
        
        /**
         * 사용자 휴대폰 번호
         * 네이버에 등록된 휴대폰 번호
         */
        @JsonProperty("mobile")
        private String mobile;
    }
}
