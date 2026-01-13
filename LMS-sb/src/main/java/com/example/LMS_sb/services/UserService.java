package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.UserDto;
import com.example.LMS_sb.exceptions.AccountLockedException;
import com.example.LMS_sb.exceptions.PasswordExpiredException;
import com.example.LMS_sb.exceptions.PasswordResetRequiredException;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import com.example.LMS_sb.security.UserDetailsImpl;
import com.example.LMS_sb.security.jwt.JwtAuthenticationResponse;
import com.example.LMS_sb.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserSecurityService userSecurityService;
    private final UserSecurityRepository userSecurityRepository;

    @Value("${maximum.login.attempts}")
    private int maxLoginAttempts;
    @Value("${password.expiry.days}")
    private long passwordExpiryDays;

    public UserService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            JwtUtils jwtUtils,
            UserSecurityService userSecurityService,
            UserSecurityRepository userSecurityRepository
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userSecurityService = userSecurityService;
        this.userSecurityRepository = userSecurityRepository;
    }

     public JwtAuthenticationResponse login(UserDto dto){

         User user = getUserByEmail(dto.getEmail());
         UserSecurity userSecurity = userSecurityService.getUserSecurityByUser(user);
         if(userSecurity.isAccountLocked() && user.getRole()!= Role.ADMIN){
             throw new AccountLockedException();
         }

         if(userSecurity.isPasswordResetRequired() && user.getRole()!= Role.ADMIN){
             throw new PasswordResetRequiredException();
         }
         if(userSecurity.getLastPasswordChange() != null && user.getRole()!= Role.ADMIN){
             long daysSincePasswordChange = ChronoUnit.DAYS.between(
                     userSecurity.getLastPasswordChange(),
                     LocalDateTime.now()
             );
             if (daysSincePasswordChange >= passwordExpiryDays) {
                 throw new PasswordExpiredException();
             }
         }
         if(!userSecurity.isFirstLogin() && userSecurity.getFailedLoginAttempts()>=maxLoginAttempts && user.getRole()!= Role.ADMIN){
             userSecurity.setAccountLocked(true);
             throw new AccountLockedException();
         }
         return authenticateUser(dto,userSecurity,user);

     }



    public JwtAuthenticationResponse authenticateUser(UserDto dto,UserSecurity security,User user){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            dto.getEmail(),
                            dto.getPassword()
                    )

            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            security.setFailedLoginAttempts(0);
            userSecurityRepository.save(security);
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails);
            return new JwtAuthenticationResponse(jwt);
        } catch (Exception e) {
            security.setFailedLoginAttempts(security.getFailedLoginAttempts() + 1);

            if (security.getFailedLoginAttempts() >= maxLoginAttempts && user.getRole() != Role.ADMIN) {
                security.setAccountLocked(true);
            }

            userSecurityRepository.save(security);

            throw e;
        }
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new UserNotFoundException(email));
    }
}
