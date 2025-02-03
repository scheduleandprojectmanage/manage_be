package com.project.scheduling.user.security;

import com.project.scheduling.common.util.JwtTokenProvider;
import com.project.scheduling.user.service.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String path = request.getRequestURI();
        log.debug("Request Path: {}", path);

        // Refresh Token 처리 로직
        if ("/api/members/refresh-token".equals(path)) {
            String refreshToken = request.getHeader("Refresh-Token");
            if (refreshToken == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No Refresh Token");
                return;
            }
            String jwtRefreshToken = refreshToken.replace("Bearer ", "");

            try {
                jwtTokenProvider.validateRefreshToken(jwtRefreshToken);
                chain.doFilter(request, response);
                return;
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Refresh Token");
                return;
            }
        }

        // Access Token 처리
        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ") &&
                SecurityContextHolder.getContext().getAuthentication() == null) {
            String token = header.substring(7);

            try {
                jwtTokenProvider.validateToken(token);
                String username = jwtTokenProvider.getEmail(token);
                var userDetails = customUserDetailsService.loadUserByUsername(username);

                var authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
                return;
            }
        }

        chain.doFilter(request, response);
    }
}