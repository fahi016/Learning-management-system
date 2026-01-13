package com.example.LMS_sb.init;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${admin.email}")
    private String adminEmail;

    @Value("${admin.password}")
    private String adminPassword;

    @PostConstruct
    public void createAdmin() {

        if (userRepository.findByEmail(adminEmail).isEmpty()) {

            User admin = new User();
            admin.setName("System Admin");
            admin.setEmail(adminEmail);
            admin.setRole(Role.ADMIN);

            userRepository.save(admin);

            UserSecurity adminSecurity = new UserSecurity();
            adminSecurity.setUser(admin);
            adminSecurity.setPasswordHash(passwordEncoder.encode(adminPassword));
            adminSecurity.setFirstLogin(false);
            adminSecurity.setPasswordResetRequired(false);
            adminSecurity.setLastPasswordChange(LocalDateTime.now());
            adminSecurity.setFailedLoginAttempts(0);
            adminSecurity.setAccountLocked(false);

            userSecurityRepository.save(adminSecurity);

            System.out.println("Admin created from ENV");
        } else {
            System.out.println("Admin already exists");
        }
    }
}
