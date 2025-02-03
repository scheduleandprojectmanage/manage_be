package com.project.scheduling.user.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.scheduling.common.exception.ApiException;
import com.project.scheduling.common.exception.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ApiException e) {
            setErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, ApiException e) throws IOException {
        response.setStatus(e.getError().getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), new ErrorResponse(e.getError().getMessage()));
    }


}