package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.UserDto;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.UserRepository;
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
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UserSecurityService userSecurityService;

    @Value("${maximum.login.attempts}")
    private int maxLoginAttempts;
    @Value("${password.expiry.days}")
    private long passwordExpiryDays;

     public JwtAuthenticationResponse checkValidityOfUserAndAuthenticate(UserDto dto){

         User user = getUserByEmail(dto.getEmail());
         UserSecurity userSecurity = userSecurityService.getUserSecurityByUser(user);
         if(userSecurity.isAccountLocked()){
             throw new RuntimeException("Account is locked. Please contact admin.");
         }

         if(userSecurity.isFirstLogin()){
             throw new RuntimeException("Password reset required for first login");
         }
         if(userSecurity.getLastPasswordChange() != null && !userSecurity.isFirstLogin() ){
             long daysSincePasswordChange = ChronoUnit.DAYS.between(
                     userSecurity.getLastPasswordChange(),
                     LocalDateTime.now()
             );
             if (daysSincePasswordChange >= passwordExpiryDays) {
                 throw new RuntimeException("Password reset required. Password expired after 30 days.");
             }
         }
         if(!userSecurity.isFirstLogin() && userSecurity.getFailedLoginAttempts()>=maxLoginAttempts){
             userSecurity.setAccountLocked(true);
             throw new RuntimeException("Account locked due to multiple failed login attempts. Please contact admin.");
         }
         return authenticateUser(dto);

     }



    public JwtAuthenticationResponse authenticateUser(UserDto dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getEmail(),
                        dto.getPassword()
                )

        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return new JwtAuthenticationResponse(jwt);
    }
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("User not found with email: "+email));
    }
}
