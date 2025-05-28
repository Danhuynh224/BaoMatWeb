package org.sale.project.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RateLimitingService {
    // Cache lưu số lần đăng nhập thất bại, tự động xóa sau 10 phút
    private final Cache<String, Integer> loginAttemptCache = Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build();

    // Cache lưu số lần yêu cầu quên mật khẩu, tự động xóa sau 5 phút
    private final Cache<String, Integer> forgotPasswordAttemptCache = Caffeine.newBuilder()
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build();

    // Kiểm tra xem IP có bị chặn đăng nhập không (quá 5 lần thất bại)
    public boolean isLoginBlocked(String ipAddress) {
        Integer attempts = loginAttemptCache.getIfPresent(ipAddress);
        return attempts != null && attempts >= 5;
    }

    // Kiểm tra xem có cần hiển thị CAPTCHA không (sau 3 lần thất bại)
    public boolean requiresCaptcha(String ipAddress) {
        Integer attempts = loginAttemptCache.getIfPresent(ipAddress);
        return attempts != null && attempts >= 3;
    }

    // Kiểm tra xem IP có bị chặn yêu cầu quên mật khẩu không (quá 3 lần)
    public boolean isForgotPasswordBlocked(String ipAddress) {
        Integer attempts = forgotPasswordAttemptCache.getIfPresent(ipAddress);
        return attempts != null && attempts >= 3;
    }

    // Ghi nhận thêm 1 lần đăng nhập thất bại
    public void recordLoginAttempt(String ipAddress) {
        Integer attempts = loginAttemptCache.getIfPresent(ipAddress);
        loginAttemptCache.put(ipAddress, attempts == null ? 1 : attempts + 1);
    }

    // Reset số lần đăng nhập thất bại
    public void resetLoginAttempts(String ipAddress) {
        loginAttemptCache.invalidate(ipAddress);
    }

    // Ghi nhận thêm 1 lần yêu cầu quên mật khẩu thất bại
    public void recordForgotPasswordAttempt(String ipAddress) {
        Integer attempts = forgotPasswordAttemptCache.getIfPresent(ipAddress);
        forgotPasswordAttemptCache.put(ipAddress, attempts == null ? 1 : attempts + 1);
    }
}