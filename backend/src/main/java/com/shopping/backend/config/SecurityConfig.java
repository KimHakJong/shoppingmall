package com.shopping.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.shopping.backend.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    //AuthenticationManager가 인자로 받을 AuthenticationConfiguraion 객체 생성자 주입
    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    public SecurityConfig(AuthenticationConfiguration authenticationConfiguration, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }
        
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf((auth) -> auth.disable());
        http            
            .formLogin((auth) -> auth.disable());
        http            
            .httpBasic((auth) -> auth.disable());
        http            
            .authorizeRequests((auth) -> auth
                .antMatchers("/api/users/join","/api/users/login","/api/users/refresh","/api/users/logout").permitAll()
                .anyRequest().authenticated()); //join, login, refresh 외엔 인증 필요
         // JWT 인증 필터 추가
        http
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http
            .sessionManagement((session) -> session
                .sessionCreationPolicy((SessionCreationPolicy.STATELESS)));
            

        return http.build();
    }

}