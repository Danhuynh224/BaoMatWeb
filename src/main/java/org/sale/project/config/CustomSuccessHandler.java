package org.sale.project.config;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
import jakarta.mail.MessagingException;
import org.sale.project.entity.Account;
import org.sale.project.entity.User;
import org.sale.project.service.AccountService;
import org.sale.project.service.OrderService;
import org.sale.project.service.UserService;
import org.sale.project.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class CustomSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private EmailService emailService;
    @Value("${name.host}")
    private String host;


    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException {
        String userEmail = "";
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();  // thường username là email
        } else if (principal instanceof String) {
            userEmail = (String) principal;
        }
        String userAgentString = request.getHeader("User-Agent");
        UserAgent userAgent = UserAgent.parseUserAgentString(userAgentString);
        Browser browser = userAgent.getBrowser();
        OperatingSystem os = userAgent.getOperatingSystem();

        String browserName = (browser != null) ? browser.getName() : "Unknown browser";
        String osName = (os != null) ? os.getName() : "Unknown OS";
        String deviceType = (os != null) ? os.getDeviceType().getName() : "Unknown device";

        String infoDevice = String.format("Thiết bị: %s trên hệ điều hành %s (%s)", browserName, osName, deviceType);

        try {
            emailService.sendLoginNotificationEmail(userEmail,infoDevice);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {

            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request, authentication);

    }

    protected void clearAuthenticationAttributes(@org.jetbrains.annotations.NotNull HttpServletRequest request, Authentication authentication) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);

        String email = authentication.getName();
        User user = userService.findUserByEmail(email);

        Account account = accountService.findByEmail(email);

        session.setAttribute("totalAnnounce", orderService.totalAnnounce());
        session.setAttribute("host", host);
        System.out.println("ss host: " + host);

        if(account != null){
            session.setAttribute("email", email);
            System.out.println("email " + email );
            session.setAttribute("isAdmin", account.getRole().getName().equals("ADMIN"));
        }


        if (user != null) {

            session.setAttribute("id", user.getId());
            session.setAttribute("sum", user.getCart() == null || user.getCart().getCartItems() == null ? 0
                    : user.getCart().getCartItems().size());
        }

    }

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final java.util.Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        throw new IllegalStateException();
    }

}
