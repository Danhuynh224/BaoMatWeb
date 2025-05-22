package org.sale.project.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sale.project.service.RateLimitingService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class LoginAttemptFilter extends OncePerRequestFilter {

    private final RateLimitingService rateLimitingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Chỉ kiểm tra với request POST tới /login
        if (request.getMethod().equals("POST") && request.getRequestURI().equals("/login")) {
            String ipAddress = getClientIP(request);
            String username = request.getParameter("username");
            
            log.info("Login attempt from IP: {} for username: {} at {}", 
                    ipAddress, username, LocalDateTime.now());
            
            if (rateLimitingService.isLoginBlocked(ipAddress)) {
                log.warn("IP {} is blocked due to too many failed attempts at {}", 
                        ipAddress, LocalDateTime.now());
                // Chuyển hướng về trang login với thông báo lỗi
                response.sendRedirect("/login?error=blocked");
                return;
            }
        }
        
        filterChain.doFilter(request, response);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 