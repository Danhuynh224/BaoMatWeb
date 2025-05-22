package org.sale.project.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sale.project.service.RateLimitingService;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    
    private final RateLimitingService rateLimitingService;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                      AuthenticationException exception) throws IOException, ServletException {
        
        String ipAddress = getClientIP(request);
        String username = request.getParameter("username");
        
        log.warn("Failed login attempt - IP: {}, Username: {}, Reason: {}, Time: {}", 
                ipAddress, username, exception.getMessage(), LocalDateTime.now());
        
        rateLimitingService.recordLoginAttempt(ipAddress);

        if (rateLimitingService.isLoginBlocked(ipAddress)) {
            log.error("Account locked - IP: {}, Username: {}, Time: {}", 
                    ipAddress, username, LocalDateTime.now());
            // Redirect to login page with too many attempts message
            super.setDefaultFailureUrl("/login?error=blocked");
            super.onAuthenticationFailure(request, response, exception);
            return;
        }

        super.setDefaultFailureUrl("/login?error=true");
        super.onAuthenticationFailure(request, response, exception);
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }
} 