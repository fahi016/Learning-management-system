package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.AdminDto;
import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.dtos.LoginStudentDto;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.security.UserDetailsImpl;
import com.example.LMS_sb.security.jwt.JwtAuthenticationResponse;
import com.example.LMS_sb.security.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;
    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;

    public void createStudent(CreateStudentRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        Student student = new Student();
        student.setRollNumber(dto.getRollNumber());
        student.setDepartment(dto.getDepartment());
        student.setUser(user);

        studentRepository.save(student);

    }

    public JwtAuthenticationResponse authenticateStudent(LoginStudentDto dto){
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
