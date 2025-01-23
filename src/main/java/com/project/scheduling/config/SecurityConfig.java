package com.project.scheduling.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        //HTTP 요청이 들어올 때 어떤 사용자가 어떤 자원에 접근할 수 있는지 정하는 보안 규칙들의 모음
        //클럽의 입구 보안 체크리스트와 같다고.. 함
        //1. 신분증 확인(인증), 2. VIP인지 일반인지 확인(권한) 3. 출입 가능한 입구 확인(접근 제어)

        //HttpSecurity는 보안 설정을 도와주는 빌더(builder)클래스임. Spring security가 자동으로 주입해주는데,
        //이를 통해서 개발자가 직접 규칙을 커스터마이즈할 수 있음

        http.csrf((auth) -> auth.disable()) //csrf disable
                .formLogin((auth) -> auth.disable()) //form 로그인방식 disable
                .httpBasic((auth) -> auth.disable()) //http basic 인증방식 disable
                .authorizeHttpRequests((auth) -> auth //경로 별 인가 작업
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .sessionManagement((session) -> session //세션 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));
                        // -> JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정하는 것이 중요하다.

        return http.build();
    }

}
