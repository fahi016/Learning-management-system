package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.exceptions.IllegalOperationException;
import com.example.LMS_sb.exceptions.PasswordOperationException;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminService {
    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Value("${admin.email}")
    private String adminEmail;


    public AdminService(UserService userService, UserSecurityService userSecurityService, UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public AdminMeDto converToDto(User privateUser) {
        AdminMeDto dto = new AdminMeDto();
        dto.setName(privateUser.getName());
        dto.setEmail(privateUser.getEmail());
        dto.setRole(privateUser.getRole());
        return dto;
    }

    public void unlockUser(UnlockUserDto dto) {
        if (dto.getEmail().equals(adminEmail)) {
            throw new IllegalOperationException("Cannot unlock your own account");
        }
        User user = userService.getUserByEmail(dto.getEmail());
        UserSecurity userSecurity = userSecurityService.getUserSecurityByUser(user);
        userSecurity.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        userSecurity.setAccountLocked(false);
        userSecurity.setFailedLoginAttempts(0);
        userSecurity.setPasswordResetRequired(true);
        userSecurityRepository.save(userSecurity);

    }

    public void changePassword(String email, ChangePasswordDto dto) {
        User user = userService.getUserByEmail(email);
        UserSecurity security = userSecurityService.getUserSecurityByUser(user);

        if(!passwordEncoder.matches(dto.getCurrentPassword(), security.getPasswordHash())){
            throw new PasswordOperationException("CURRENT_PASSWORD_INCORRECT");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), security.getPasswordHash())) {
            throw new PasswordOperationException("NEW_PASSWORD_MUST_BE_DIFFERENT");
        }
        security.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        security.setLastPasswordChange(LocalDateTime.now());
        security.setFirstLogin(false);
        security.setPasswordResetRequired(false);
        security.setFailedLoginAttempts(0);
        security.setAccountLocked(false);

        userSecurityRepository.save(security);
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new GetUserDto(
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();



    }
}
