package com.example.delivery.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // REST API에서는 비활성화
                .formLogin(AbstractHttpConfigurer::disable) // 로그인 페이지 비활성화 (추가)
                .httpBasic(Customizer.withDefaults()) // 또는 JWT 등 다른 인증 방식 사용 (추가)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/member/**").permitAll() // 공개 API
                        .requestMatchers(  "/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers("/api/private/**").authenticated() // 인증 필요
                        .anyRequest().denyAll() // 기본 인증 (JWT로 대체 예정)
                );

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
