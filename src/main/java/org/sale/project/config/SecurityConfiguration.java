package org.sale.project.config;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.sale.project.service.AccountService;
import org.sale.project.service.CustomUserDetailsService;
import org.sale.project.service.RateLimitingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;
import org.springframework.web.filter.OncePerRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final RateLimitingService rateLimitingService;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;
    private final LoginAttemptFilter loginAttemptFilter;

    @Bean
    public OncePerRequestFilter rateLimitingFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
                    throws ServletException, IOException {

                String ipAddress = getClientIP(request);
                String path = request.getRequestURI();

                // Kiểm tra giới hạn đăng nhập
                if (path.equals("/login") && request.getMethod().equals("POST")) {
                    if (rateLimitingService.isLoginBlocked(ipAddress)) {
                        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                        response.getWriter().write("Too many login attempts. Please try again later.");
                        return;
                    }
                }

                // Kiểm tra giới hạn quên mật khẩu
                if (path.equals("/forgot") && request.getMethod().equals("POST")) {
                    if (rateLimitingService.isForgotPasswordBlocked(ipAddress)) {
                        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
                        response.getWriter().write("Too many password reset attempts. Please try again later.");
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
        };
    }

    // Cấu hình fillter
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(loginAttemptFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                DispatcherType.INCLUDE)
                        .permitAll()

                        .requestMatchers("/","/register", "/login", "/product/**", "/client/**", "/css/**", "/js/**",
                                "/images/**", "/email", "/google", "/facebook", "/payment/**", "/forgot", "/blog", "/about")
                        .permitAll()

                        .requestMatchers("/admin/**", "/feedBack/**").hasRole("ADMIN")

                        .requestMatchers("/cart/**", "/order/**", "/review/**", "/pay/**", "/account/**" , "/apply/**").hasRole("USER")

                        .anyRequest().authenticated())

                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices()))

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureHandler(authenticationFailureHandler)
                        .successHandler(authenticationSuccessHandler())
                        .permitAll())

                .sessionManagement(session -> session
                        .maximumSessions(-1)
                        .expiredUrl("/login?expired") // chuyển hướng khi session bị hết hạn
                        .maxSessionsPreventsLogin(false)
                        .sessionRegistry(sessionRegistry())
                )
                .exceptionHandling(ex -> ex.accessDeniedPage("/404"));
        return http.build();
    }

    // Lựa chọn Authentication Provider
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //cấu hình Bước 2
    @Bean
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public UserDetailsService userDetailsService(AccountService accountService, RateLimitingService rateLimitingService) {
        return new CustomUserDetailsService(accountService, rateLimitingService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpFirewall allowUrlWithDoubleSlash() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowUrlEncodedDoubleSlash(true); // Cho phép "//"
        return firewall;
    }

    // cấu hình bước 5
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomSuccessHandler();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public static HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
}