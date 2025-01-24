package com.project.scheduling.common.config;

import com.project.scheduling.user.security.JwtAuthenticationFilter;
import com.project.scheduling.common.util.JwtTokenProvider;
import com.project.scheduling.user.security.JwtExceptionFilter;
import com.project.scheduling.user.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;


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

        http.cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:3000/"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setMaxAge(3600L); //1시간
                return config;
            }
        }));

        http.csrf((auth) -> auth.disable()) //csrf disable
                .formLogin((auth) -> auth.disable()) //form 로그인방식 disable
                .httpBasic((auth) -> auth.disable()) //http basic 인증방식 disable
                .authorizeHttpRequests((auth) -> auth //경로 별 인가 작업
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/user/signup").permitAll()
                        .requestMatchers("/user/login").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN")

                        /*// 모두 허용
                        // "/api/v1/auth/n/**" 라고 설정해줄 수도 있음. => 사용자 토큰 없이(n) 접근 허용.
                        .requestMatchers("/api/v1/auth/n/**").permitAll()

                        // 관리자=선생님(ROLE_ADMIN)만 접근 허용
                        // "/api/v1/auth/admin/**" 라고 설정해줄 수도 있음.
                        //.requestMatchers("/admin/**").hasRole("ADMIN") => ROLE_ 접두사가 자동으로 들어감.
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")

                        // 로그인한 사용자(ROLE_USER)만 접근 허용
                        // "/api/v1/auth/y/**" 라고 설정해줄 수도 있음.
                        .requestMatchers("/api/v1/auth/y/**").hasAnyAuthority("ROLE_USER")

                        // TODO: 최고 권한의 관리자 필요함. ( 필수는 아님. 추후 고려해보기. )
                        // 나머지 요청은 인증된 사용자에게만 접근 허용*/


                        .anyRequest().authenticated())
                .sessionManagement((session) -> session //세션 설정
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                        // -> JWT를 통한 인증/인가를 위해서 세션을 STATELESS 상태로 설정하는 것이 중요하다.
                // 필터 통해서 토큰 기반 로그인을 할거다.
                // UsernamePasswordAuthenticationFilter 이전에 JwtAuthenticationFilter가 실행되도록 등록
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, customUserDetailsService),
                        UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JwtExceptionFilter(), JwtAuthenticationFilter.class);

        return http.build();
    }

}
