package com.example.LMS_sb.services;

import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.exceptions.IllegalOperationException;
import com.example.LMS_sb.exceptions.PasswordOperationException;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.TeacherRepository;
import com.example.LMS_sb.repository.UserRepository;
import com.example.LMS_sb.repository.UserSecurityRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdminService {
    private final UserService userService;
    private final UserSecurityService userSecurityService;
    private final UserSecurityRepository userSecurityRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Value("${admin.email}")
    private String adminEmail;


    public AdminService(UserService userService, UserSecurityService userSecurityService, UserSecurityRepository userSecurityRepository, PasswordEncoder passwordEncoder, UserRepository userRepository, StudentRepository studentRepository, TeacherRepository teacherRepository) {
        this.userService = userService;
        this.userSecurityService = userSecurityService;
        this.userSecurityRepository = userSecurityRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
    }

    public AdminMeDto converToDto(User privateUser) {
        AdminMeDto dto = new AdminMeDto();
        dto.setName(privateUser.getName());
        dto.setEmail(privateUser.getEmail());
        dto.setRole(privateUser.getRole());
        return dto;
    }
    @Transactional
    public void unlockUser(UnlockUserDto dto) {
        if (dto.getEmail().equals(adminEmail)) {
            throw new IllegalOperationException("Cannot unlock your own account");
        }
        User user = userService.getUserByEmail(dto.getEmail());
        UserSecurity userSecurity = userSecurityService.getUserSecurityByUser(user);
        userSecurity.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        userSecurity.setAccountLocked(false);
        userSecurity.setFailedLoginAttempts(0);
        userSecurity.setPasswordResetRequired(true);
        userSecurityRepository.save(userSecurity);

    }

    public void changePassword(String email, ChangePasswordDto dto) {
        User user = userService.getUserByEmail(email);
        UserSecurity security = userSecurityService.getUserSecurityByUser(user);

        if(!passwordEncoder.matches(dto.getCurrentPassword(), security.getPasswordHash())){
            throw new PasswordOperationException("CURRENT_PASSWORD_INCORRECT");
        }
        if (passwordEncoder.matches(dto.getNewPassword(), security.getPasswordHash())) {
            throw new PasswordOperationException("NEW_PASSWORD_MUST_BE_DIFFERENT");
        }
        security.setPasswordHash(passwordEncoder.encode(dto.getNewPassword()));
        security.setLastPasswordChange(LocalDateTime.now());
        security.setFirstLogin(false);
        security.setPasswordResetRequired(false);
        security.setFailedLoginAttempts(0);
        security.setAccountLocked(false);

        userSecurityRepository.save(security);
    }

    public List<GetUserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(user -> new GetUserDto(
                        user.getName(),
                        user.getEmail(),
                        user.getRole()
                ))
                .toList();



    }

    public GetUserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new GetUserDto(
                user.getName(),
                user.getEmail(),
                user.getRole()
        );
    }
    @Transactional
    public void updateUserById(Long id, GetUserDto dto) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setRole(dto.getRole());
        userRepository.save(user);
    }

    public void deleteUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    public List<StudentDto> getAllStudents() {
        return studentRepository.findAll().stream()
                .map(student-> new StudentDto(
                        student.getUser().getName(),
                        student.getUser().getEmail(),
                        student.getRollNumber(),
                        student.getDepartment()

                )).toList();

    }

    public StudentDto getStudentById(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new StudentDto(
                student.getUser().getName(),
                student.getUser().getEmail(),
                student.getRollNumber(),
                student.getDepartment()
        );
    }
    @Transactional
    public void updateStudentById(long id, StudentDto dto) {
        Student student = studentRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User user = student.getUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        student.setRollNumber(dto.getRollNumber());
        student.setDepartment(dto.getDepartment());
        student.setUser(user);
        studentRepository.save(student);

    }
    @Transactional
    public void deleteStudentById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(student.getUser());
    }

    public List<TeacherDto> getAllTeachers() {
        return teacherRepository.findAll().stream()
                .map(teacher-> new TeacherDto(
                        teacher.getUser().getName(),
                        teacher.getUser().getEmail(),
                        teacher.getExpertise()
                        
                )).toList();
    }

    public void updateTeacherById(Long id, TeacherDto dto) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(UserNotFoundException::new);
        User user = teacher.getUser();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        teacher.setExpertise(dto.getExpertise());
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    public TeacherDto getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(UserNotFoundException::new);
        return new TeacherDto(
                teacher.getUser().getName(),
                teacher.getUser().getEmail(),
                teacher.getExpertise()
        );
    }

    public void deleteTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        userRepository.delete(teacher.getUser());
    }
}
