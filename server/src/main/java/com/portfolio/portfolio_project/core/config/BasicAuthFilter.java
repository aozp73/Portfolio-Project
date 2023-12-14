package com.portfolio.portfolio_project.core.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.filter.OncePerRequestFilter;

public class BasicAuthFilter extends OncePerRequestFilter {
    private final String expectedCredentials;

    public BasicAuthFilter(String expectedCredentials) {
        this.expectedCredentials = expectedCredentials;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String requestUri = request.getRequestURI();

        if ("/actuator/prometheus".equals(requestUri)) {
            String authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("Basic ")) {
                String credentials = authHeader.substring(6).trim();
                if (expectedCredentials.equals(credentials)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"data\":\"권한이 없습니다.\"}");
            return;
        }

        filterChain.doFilter(request, response);
    }
}