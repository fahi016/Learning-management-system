package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentService {

    private UserRepository userRepository;
    private StudentRepository studentRepository;
    private PasswordEncoder passwordEncoder;

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

}
