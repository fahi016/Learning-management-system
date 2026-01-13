package com.example.LMS_sb.services;

import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserSecurityRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserSecurityService {


    private UserSecurityRepository userSecurityRepository;
    private PasswordEncoder passwordEncoder;

    public void createForNewUser(User user,String password){
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUser(user);
        userSecurity.setPasswordHash(passwordEncoder.encode(password));
        userSecurity.setLastPasswordChange(LocalDateTime.now());
        userSecurityRepository.save(userSecurity);

    }
    public UserSecurity getUserSecurityByUser(User user){
         return userSecurityRepository.findUserSecurityByUser(user).orElseThrow(()->
                 new RuntimeException("Security record not found")
                 );
    }

    public void resetPassword(UserSecurity security,String newPassword){
        security.setPasswordHash(passwordEncoder.encode(newPassword));
        security.setFirstLogin(false);
        security.setPasswordResetRequired(false);
        security.setFailedLoginAttempts(0);
        security.setAccountLocked(false);
        security.setLastPasswordChange(LocalDateTime.now());
        userSecurityRepository.save(security);
    }

    public void recordFailedAttempt(UserSecurity security, int maxAttempts){
        security.setFailedLoginAttempts(security.getFailedLoginAttempts()+1);
        if(security.getFailedLoginAttempts()>=maxAttempts){
            security.setAccountLocked(true);
        }
        userSecurityRepository.save(security);
    }
    public void resetFailedAttempt(UserSecurity security){
        security.setFailedLoginAttempts(0);
        userSecurityRepository.save(security);
    }

}
