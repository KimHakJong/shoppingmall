package com.shopping.backend.repository;

import com.shopping.backend.entity.OAuthUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * OAuth 사용자 Repository 인터페이스
 * OAuth 사용자 정보 조회, 저장, 수정 등을 담당
 */
@Repository
public interface OAuthUsersRepository extends JpaRepository<OAuthUsers, Long> {

    /**
     * OAuth 제공자와 제공자 사용자 ID로 OAuth 사용자 조회
     * 
     * @param provider OAuth 제공자 (NAVER, KAKAO, GOOGLE 등)
     * @param providerUserId OAuth 제공자에서 제공하는 고유 ID
     * @return OAuth 사용자 정보 (Optional)
     */
    Optional<OAuthUsers> findByProviderAndProviderUserId(String provider, String providerUserId);

    /**
     * 사용자 ID로 OAuth 사용자 목록 조회
     * 한 사용자가 여러 OAuth 계정을 연결할 수 있음
     * 
     * @param userId 사용자 ID
     * @return OAuth 사용자 목록
     */
    Optional<OAuthUsers> findByUserId(String userId);

    /**
     * 사용자 ID와 OAuth 제공자로 OAuth 사용자 조회
     * 
     * @param userId 사용자 ID
     * @param provider OAuth 제공자
     * @return OAuth 사용자 정보 (Optional)
     */
    Optional<OAuthUsers> findByUserIdAndProvider(String userId, String provider);

    /**
     * OAuth 제공자로 OAuth 사용자 목록 조회
     * 
     * @param provider OAuth 제공자
     * @return OAuth 사용자 목록
     */
    // List<OAuthUsers> findByProvider(String provider);

    // /**
    //  * 활성화된 OAuth 사용자만 조회
    //  * 
    //  * @param provider OAuth 제공자
    //  * @param isActive 활성화 여부 (Y/N)
    //  * @return 활성화된 OAuth 사용자 목록
    //  */
    // List<OAuthUsers> findByProviderAndIsActive(String provider, String isActive);

    // /**
    //  * 만료된 액세스 토큰을 가진 OAuth 사용자 조회
    //  * 
    //  * @param currentTime 현재 시간
    //  * @return 만료된 토큰을 가진 OAuth 사용자 목록
    //  */
    // @Query("SELECT o FROM OAuthUser o WHERE o.accessTokenExpiresAt < :currentTime AND o.isActive = 'Y'")
    // List<OAuthUsers> findExpiredAccessTokens(@Param("currentTime") java.time.LocalDateTime currentTime);

    // /**
    //  * 특정 사용자의 활성화된 OAuth 계정 수 조회
    //  * 
    //  * @param userId 사용자 ID
    //  * @return 활성화된 OAuth 계정 수
    //  */
    // @Query("SELECT COUNT(o) FROM OAuthUser o WHERE o.userId = :userId AND o.isActive = 'Y'")
    // long countActiveOAuthAccountsByUserId(@Param("userId") String userId);

    // /**
    //  * OAuth 제공자별 사용자 수 조회
    //  * 
    //  * @param provider OAuth 제공자
    //  * @return 해당 제공자로 가입한 사용자 수
    //  */
    // @Query("SELECT COUNT(o) FROM OAuthUser o WHERE o.provider = :provider AND o.isActive = 'Y'")
    // long countUsersByProvider(@Param("provider") String provider);
}
