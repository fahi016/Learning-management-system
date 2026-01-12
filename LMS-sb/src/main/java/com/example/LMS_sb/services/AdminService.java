package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.AdminMeDto;
import com.example.LMS_sb.models.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    public AdminMeDto converToDto(User privateUser) {
        AdminMeDto dto = new AdminMeDto();
        dto.setName(privateUser.getName());
        dto.setEmail(privateUser.getEmail());
        dto.setRole(privateUser.getRole());
        return dto;
    }
}
