package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateTeacherRequestDto;
import com.example.LMS_sb.dtos.TeacherMeDto;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.TeacherRepository;
import com.example.LMS_sb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TeacherService {

    private UserRepository userRepository;
    private TeacherRepository teacherRepository;
    private PasswordEncoder passwordEncoder;


    public void createTeacher(CreateTeacherRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.TEACHER);

        userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setExpertise(dto.getExpertise());
        teacher.setUser(user);

        teacherRepository.save(teacher);

    }

    public Teacher getTeacherByUserEmail(String email) {
        return teacherRepository.findByUserEmail(email).orElseThrow(()-> new RuntimeException("Teacher not found with email: "+email));
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
