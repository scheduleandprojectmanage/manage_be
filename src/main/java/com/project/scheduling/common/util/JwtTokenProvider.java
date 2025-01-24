package com.project.scheduling.common.util;

import com.project.scheduling.common.exception.ApiException;
import com.project.scheduling.common.exception.ExceptionEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class JwtTokenProvider {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Key key;

    public JwtTokenProvider(RedisTemplate<String, Object> redisTemplate, @Value("${spring.jwt.secret}") String secretKey) {
        this.redisTemplate = redisTemplate;
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        byte[] keyBytes = Decoders.BASE64.decode(encodedKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateAccessToken(String email) {
        return generateToken(email, 1000 * 60 * 15); // 15분
    }

    public String generateRefreshToken(String email) {
        String refreshToken = generateToken(email, 1000 * 60 * 60 * 24 * 7); // 7일
        saveRefreshToken(email, refreshToken);
        return refreshToken;
    }

    private String generateToken(String email, long expiration) {
        Date now = new Date();
        Claims claims = Jwts.claims().setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + expiration))
                .signWith(key)
                .compact();
    }

    public void validateToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                throw new ApiException(ExceptionEnum.BLACKLISTED_TOKEN);
            }
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature: {}", token);
            throw new ApiException(ExceptionEnum.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", token);
            throw new ApiException(ExceptionEnum.TIMEOUT_TOKEN);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", token);
            throw new ApiException(ExceptionEnum.UNSUPPORTED_TOKEN);
        } catch (Exception e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            throw new ApiException(ExceptionEnum.INVALID_TOKEN);
        }
    }

    public boolean validateRefreshToken(String token) {
        try {
            if (isTokenBlacklisted(token)) {
                return false;
            }

            // Validate token format/signature first
            try {
                Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(token);
            } catch (Exception e) {
                return false;
            }

            // Check if stored token matches
            String email = getEmail(token);
            String storedToken = (String) redisTemplate.opsForValue().get("refreshToken:" + email);

            return token.equals(storedToken);
        } catch (Exception e) {
            log.error("Error validating refresh token: {}", e.getMessage());
            return false;
        }
    }
    public String getEmail(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        } catch (Exception e) {
            log.error("Error extracting email from token: {}", e.getMessage());
            throw new ApiException(ExceptionEnum.INVALID_TOKEN);
        }
    }

    public void invalidateToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            long expiration = claims.getExpiration().getTime() - System.currentTimeMillis();
            redisTemplate.opsForValue().set(token, "blacklisted", expiration, TimeUnit.MILLISECONDS);
            log.debug("Token blacklisted successfully: {}", token);
        } catch (Exception e) {
            log.error("Error invalidating token: {}", e.getMessage());
            throw new ApiException(ExceptionEnum.INVALID_TOKEN);
        }
    }

    private boolean isTokenBlacklisted(String token) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(token));
    }

    private void saveRefreshToken(String email, String refreshToken) {
        try {
            redisTemplate.opsForValue().set(
                    "refreshToken:" + email,
                    refreshToken,
                    7,
                    TimeUnit.DAYS
            );
        } catch (Exception e) {
            log.error("Error saving refresh token: {}", e.getMessage());
            throw new ApiException(ExceptionEnum.SERVER_ERROR);
        }
    }

    public String refreshAccessToken(String refreshToken) {
        try {
            String email = getEmail(refreshToken);
            String storedToken = (String) redisTemplate.opsForValue().get("refreshToken:" + email);

            if (!refreshToken.equals(storedToken)) {
                throw new ApiException(ExceptionEnum.TOKEN_MISMATCH);
            }

            return generateAccessToken(email);
        } catch (ApiException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error refreshing access token: {}", e.getMessage());
            throw new ApiException(ExceptionEnum.INVALID_TOKEN);
        }
    }
}