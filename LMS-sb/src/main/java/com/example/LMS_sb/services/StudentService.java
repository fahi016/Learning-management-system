package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.dtos.StudentDto;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.enums.Role;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class StudentService {

    private UserRepository userRepository;
    private UserService userService;
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
        return studentRepository.findByUserEmail(email).orElseThrow(UserNotFoundException::new);

    }

    public StudentDto convertToDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setName(student.getUser().getName());
        dto.setEmail(student.getUser().getEmail());
        dto.setRollNumber(student.getRollNumber());
        dto.setDepartment(student.getDepartment());
        return dto;
    }

    public StudentDto getMyProfile(Authentication authentication) {
            Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
            return new StudentDto(
                    student.getUser().getName(),
                    student.getUser().getEmail(),
                    student.getRollNumber(),
                    student.getDepartment()

            );
    }
}
