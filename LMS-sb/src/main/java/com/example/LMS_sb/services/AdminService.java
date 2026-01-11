package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.AdminDto;
import com.example.LMS_sb.security.UserDetailsImpl;
import com.example.LMS_sb.security.jwt.JwtAuthenticationResponse;
import com.example.LMS_sb.security.jwt.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;


    public JwtAuthenticationResponse authenticateAdmin(AdminDto dto){
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
}
