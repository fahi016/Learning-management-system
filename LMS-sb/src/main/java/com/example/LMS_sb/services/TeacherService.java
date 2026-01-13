package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateTeacherRequestDto;
import com.example.LMS_sb.dtos.TeacherMeDto;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.TeacherRepository;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class TeacherService {

    private UserRepository userRepository;
    private TeacherRepository teacherRepository;
    private PasswordEncoder passwordEncoder;
    private UserSecurityRepository userSecurityRepository;
    private UserSecurityService userSecurityService;

    @Transactional
    public void createTeacher(CreateTeacherRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.TEACHER);

        userRepository.save(user);

        userSecurityService.createForNewUser(user,dto.getPassword());


        Teacher teacher = new Teacher();
        teacher.setExpertise(dto.getExpertise());
        teacher.setUser(user);

        teacherRepository.save(teacher);

    }

    public Teacher getTeacherByUserEmail(String email) {
        return teacherRepository.findByUserEmail(email).orElseThrow(()-> new UserNotFoundException());
    }

    public TeacherMeDto convertToDto(Teacher teacher) {
        TeacherMeDto dto = new TeacherMeDto();
        dto.setName(teacher.getUser().getName());
        dto.setEmail(teacher.getUser().getEmail());
        dto.setRole(teacher.getUser().getRole());
        dto.setExpertise(teacher.getExpertise());
        return dto;
    }
}
