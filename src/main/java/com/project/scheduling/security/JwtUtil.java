//package com.project.scheduling.security;
//
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import io.jsonwebtoken.security.Keys;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;
//import java.nio.charset.StandardCharsets;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class JwtUtil {
///* JWT 토큰 생성, 서명, 검증하는 기능 제공*/
//
//
//    private final SecretKey secretKey;
//
//    @Value("${spring.jwt.secret}")
//    private String SECRET_KEY;
//
//    public JwtUtil(String SECRET_KEY) {
//        secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
//    }
//
//    public String getUsername(String token) {
//        return Jwts.parserBuilder()
//                .setSigningKey(secretKey)
//                .build()
//                .parseClaimsJws(token)
//                .getSignature()
//                .get("username", String.class);
//
//    }
//
//}
