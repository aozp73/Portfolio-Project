package com.portfolio.portfolio_project.core.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class ApiKeyAuthFilter extends OncePerRequestFilter {
    private final String apiKey;

    public ApiKeyAuthFilter(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        // API 키 검증은 '/actuator/prometheus' 경로에서만 수행
        if ("/actuator/prometheus".equals(requestUri)) {
            String requestApiKey = request.getHeader("X-API-KEY");
            if (apiKey.equals(requestApiKey)) {
                filterChain.doFilter(request, response);
                return;
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType("application/json");
                response.getWriter().write("{\"data\":\"권한이 없습니다.\"}");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }
}