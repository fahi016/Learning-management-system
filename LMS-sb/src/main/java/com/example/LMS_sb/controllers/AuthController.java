package com.example.LMS_sb.controllers;


import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.security.UserDetailsImpl;
import com.example.LMS_sb.services.AdminService;
import com.example.LMS_sb.services.StudentService;
import com.example.LMS_sb.services.TeacherService;
import com.example.LMS_sb.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class AuthController {

    private StudentService studentService;
    private TeacherService teacherService;
    private UserService userService;
    private AdminService adminService;


    @PostMapping("/api/auth/register/student")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerStudent(@RequestBody CreateStudentRequestDto dto){
              studentService.createStudent(dto);
              return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PostMapping("/api/auth/register/teacher")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerTeacher(@RequestBody CreateTeacherRequestDto dto){
        teacherService.createTeacher(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDto dto){

        return ResponseEntity.ok(userService.checkValidityOfUserAndAuthenticate(dto));

    }
    @GetMapping("/api/auth/me")
    public ResponseEntity<?> getLoggedUser(Authentication authentication){
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        switch (user.getRole()) {
            case "STUDENT" -> {
                Student student = studentService.getStudentByUserEmail(user.getEmail());
                StudentMeDto dto = studentService.convertToDto(student);
                return ResponseEntity.ok(dto);
            }
            case "TEACHER" -> {
                Teacher teacher = teacherService.getTeacherByUserEmail(user.getEmail());
                TeacherMeDto dto = teacherService.convertToDto(teacher);
                return ResponseEntity.ok(dto);


            }
            case "ADMIN" -> {
                User privateUser = userService.getUserByEmail(user.getEmail());
                AdminMeDto dto = adminService.converToDto(privateUser);
                return ResponseEntity.ok(dto);


            }
        }
        throw new RuntimeException("Invalid Role");
    }

}
