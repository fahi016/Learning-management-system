package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.AdminMeDto;
import com.example.LMS_sb.dtos.UnlockUserDto;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private UserService userService;
    private UserSecurityService userSecurityService;
    private UserSecurityRepository userSecurityRepository;

    public AdminMeDto converToDto(User privateUser) {
        AdminMeDto dto = new AdminMeDto();
        dto.setName(privateUser.getName());
        dto.setEmail(privateUser.getEmail());
        dto.setRole(privateUser.getRole());
        return dto;
    }

    public void unlockUser(UnlockUserDto dto) {
        User user = userService.getUserByEmail(dto.getEmail());
        UserSecurity userSecurity = userSecurityService.getUserSecurityByUser(user);
        userSecurity.setAccountLocked(false);
        userSecurity.setFailedLoginAttempts(0);
        userSecurity.setPasswordResetRequired(true);
        userSecurityRepository.save(userSecurity);

    }
}
