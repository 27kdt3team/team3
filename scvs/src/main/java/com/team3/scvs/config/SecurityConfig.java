package com.team3.scvs.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll() // 모든 요청은 인증 불필요
            )
            .formLogin(form -> form
                .loginPage("/login") // 로그인 페이지 URL
                .loginProcessingUrl("/login") // 로그인 요청 처리 URL
                .defaultSuccessUrl("/") // 로그인 성공 시 리다이렉트
                .usernameParameter("email") // email(DB)를 username(Security)으로 변경
            )
            .logout(form -> form
                .logoutUrl("/logout")   // 로그아웃 페이지 URL
                .logoutSuccessUrl("/") // 로그아웃 성공 시 리다이렉트
            );
        return http.build();
    }


}
