package com.example.LMS_sb.services;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserSecurityService {
    private UserSecurityRepository userSecurityRepository;
    public UserSecurity getUserSecurityByUser(User user){
         return userSecurityRepository.findUserSecurityByUser(user).orElseThrow(()->
                 new RuntimeException("User not found with details: " +user)
                 );
    }
}
