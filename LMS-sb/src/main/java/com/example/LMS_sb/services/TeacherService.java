package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateTeacherRequestDto;
import com.example.LMS_sb.dtos.TeacherDto;
import com.example.LMS_sb.dtos.UpdateMeByTeacherDto;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.TeacherRepository;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        return teacherRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);
    }

    public TeacherDto convertToDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setName(teacher.getUser().getName());
        dto.setEmail(teacher.getUser().getEmail());
        dto.setExpertise(teacher.getExpertise());
        return dto;
    }

    public TeacherDto getMyProfile(Authentication authentication) {
        Teacher teacher = teacherRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        return new TeacherDto(
                teacher.getId(),
                teacher.getUser().getName(),
                teacher.getUser().getEmail(),
                teacher.getExpertise()

        );
    }

    public void updateMyProfile(UpdateMeByTeacherDto dto, Authentication authentication) {
        Teacher teacher = teacherRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        User user = teacher.getUser();
        user.setName(dto.getName());
        teacher.setExpertise(dto.getExpertise());
        teacher.setUser(user);
        user.setTeacher(teacher);
        userRepository.save(user);
    }
}
