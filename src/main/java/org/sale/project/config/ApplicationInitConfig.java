package org.sale.project.config;


import org.sale.project.entity.Account;
import org.sale.project.entity.Role;
import org.sale.project.entity.User;
import org.sale.project.repository.AccountRepository;
import org.sale.project.repository.RoleRepository;
import org.sale.project.repository.UserRepository;
import org.sale.project.service.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class  ApplicationInitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    @Bean
    ApplicationRunner applicationRunner(AccountRepository accountRepository, RoleRepository roleRepository) {
        return args -> {
            if(roleRepository.findByName("USER") == null || roleRepository.findByName("ADMIN") == null) {
                Role roleAdmin = Role.builder()
                        .name("ADMIN")
                        .build();
                Role roleUser = Role.builder()
                        .name("USER")
                        .build();

                roleRepository.save(roleAdmin);
                roleRepository.save(roleUser);
            }

            String adminEmail = "vdan2242004@gmail.com";
            String password = generateSecurePassword();
            if(accountRepository.findByEmail(adminEmail).isEmpty()) {
                Account account = Account.builder()
                        .email(adminEmail)
                        .password(passwordEncoder.encode(password))
                        .role(roleRepository.findByName("ADMIN"))
                        .build();

                accountRepository.save(account);
            }
            try {
                System.out.println(password);
                emailService.sendAdminPasswordEmail(adminEmail, password);
            }
            catch(Exception e) {
                System.out.println(e.getMessage());
            }

        };
    }

    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL = "!@#$%^&*()-_+=<>?";

    private static final String ALL = UPPER + LOWER + DIGITS + SPECIAL;
    private static final SecureRandom random = new SecureRandom();

    public static String generateSecurePassword() {


        List<Character> passwordChars = new ArrayList<>();

        // Bắt buộc có ít nhất 1 ký tự từ mỗi loại
        passwordChars.add(UPPER.charAt(random.nextInt(UPPER.length())));
        passwordChars.add(LOWER.charAt(random.nextInt(LOWER.length())));
        passwordChars.add(DIGITS.charAt(random.nextInt(DIGITS.length())));
        passwordChars.add(SPECIAL.charAt(random.nextInt(SPECIAL.length())));

        // Các ký tự còn lại chọn ngẫu nhiên từ ALL
        for (int i = 4; i < 12; i++) {
            passwordChars.add(ALL.charAt(random.nextInt(ALL.length())));
        }

        // Trộn ngẫu nhiên các ký tự
        Collections.shuffle(passwordChars, random);

        // Chuyển thành chuỗi
        StringBuilder password = new StringBuilder();
        for (char c : passwordChars) {
            password.append(c);
        }

        return password.toString();
    }
}
