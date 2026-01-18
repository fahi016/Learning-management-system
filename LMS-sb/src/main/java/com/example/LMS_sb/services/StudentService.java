package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.CreateStudentRequestDto;
import com.example.LMS_sb.dtos.StudentDto;
import com.example.LMS_sb.dtos.UpdateMeByStudentDto;
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
@Transactional
public class StudentService {

    private UserRepository userRepository;
    private UserService userService;
    private StudentRepository studentRepository;
    private UserSecurityService userSecurityService;


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
        dto.setId(student.getId());
        dto.setName(student.getUser().getName());
        dto.setEmail(student.getUser().getEmail());
        dto.setRollNumber(student.getRollNumber());
        dto.setDepartment(student.getDepartment());
        return dto;
    }

    public StudentDto getMyProfile(Authentication authentication) {
            Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
            return new StudentDto(
                    student.getId(),
                    student.getUser().getName(),
                    student.getUser().getEmail(),
                    student.getRollNumber(),
                    student.getDepartment()

            );
    }
    public void updateMyProfile(UpdateMeByStudentDto dto, Authentication authentication) {
        Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        User user = student.getUser();
        user.setName(dto.getName());
        student.setDepartment(dto.getDepartment());
        student.setRollNumber(dto.getRollNumber());
        student.setUser(user);
        user.setStudent(student);
        userRepository.save(user);
    }
}
