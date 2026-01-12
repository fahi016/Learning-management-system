package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.dtos.StudentMeDto;
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

    public Student getStudentByUserEmail(String email){
        return studentRepository.findByUserEmail(email).orElseThrow(()->new RuntimeException("Student not found with email: "+email));

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
