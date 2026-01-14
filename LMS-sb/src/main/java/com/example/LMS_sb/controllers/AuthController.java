package com.example.LMS_sb.controllers;


import com.example.LMS_sb.dtos.*;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.models.User;
import com.example.LMS_sb.models.UserSecurity;
import com.example.LMS_sb.security.UserDetailsImpl;
import com.example.LMS_sb.services.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class AuthController {

    private StudentService studentService;
    private TeacherService teacherService;
    private UserService userService;
    private AdminService adminService;
    private UserSecurityService userSecurityService;


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

        return ResponseEntity.ok(userService.login(dto));

    }
    @GetMapping("/api/auth/me")
    public ResponseEntity<?> getLoggedUser(Authentication authentication){
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        switch (user.getRole()) {
            case "STUDENT" -> {
                Student student = studentService.getStudentByUserEmail(user.getEmail());
                StudentDto dto = studentService.convertToDto(student);
                return ResponseEntity.ok(dto);
            }
            case "TEACHER" -> {
                Teacher teacher = teacherService.getTeacherByUserEmail(user.getEmail());
                TeacherDto dto = teacherService.convertToDto(teacher);
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

    @PostMapping("/api/auth/reset-password")
    @PreAuthorize("hasAuthority('PASSWORD_RESET')")
    public ResponseEntity<?> resetFirstLoginPassword(
            @Valid @RequestBody ResetPasswordDto dto,
            Authentication authentication
    ) {

        User user = userService.getUserByEmail(authentication.getName());
        UserSecurity security = userSecurityService.getUserSecurityByUser(user);
        userSecurityService.resetPassword(security, dto.getNewPassword());

        return ResponseEntity.ok("Password reset successful");
    }
    @PostMapping("/api/auth/unlock-user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> unlockUser(@RequestBody UnlockUserDto dto){
        adminService.unlockUser(dto);
        return ResponseEntity.ok("Unlocked user successfully");
    }

    @PostMapping("/api/auth/change-password")
    @PreAuthorize("isAuthenticated() and !hasAuthority('PASSWORD_RESET')")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto dto,
      Authentication authentication
    ){
        String email = authentication.getName();
        adminService.changePassword(email,dto);
        return ResponseEntity.ok("Password changed successfully");

    }


}
