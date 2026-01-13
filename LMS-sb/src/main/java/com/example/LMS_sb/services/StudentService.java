package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.dtos.StudentMeDto;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class StudentService {

    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private UserSecurityService userSecurityService;

    @Transactional
    public void createStudent(CreateStudentRequestDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(Role.STUDENT);

        userRepository.save(user);

        userSecurityService.createForNewUser(user,dto.getPassword());

        Student student = new Student();
        student.setRollNumber(dto.getRollNumber());
        student.setDepartment(dto.getDepartment());
        student.setUser(user);

        studentRepository.save(student);

    }

    public Student getStudentByUserEmail(String email){
        return studentRepository.findByUserEmail(email).orElseThrow(()->new UserNotFoundException());

    }

    public StudentMeDto convertToDto(Student student) {
        StudentMeDto dto = new StudentMeDto();
        dto.setName(student.getUser().getName());
        dto.setEmail(student.getUser().getEmail());
        dto.setRole(student.getUser().getRole());
        dto.setRollNumber(student.getRollNumber());
        dto.setDepartment(student.getDepartment());
        return dto;
    }
}
