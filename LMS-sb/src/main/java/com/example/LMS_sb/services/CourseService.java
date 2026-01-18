package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.CourseDto;
import com.example.LMS_sb.dtos.GetCourseDto;
import com.example.LMS_sb.exceptions.CourseNotFoundException;
import com.example.LMS_sb.exceptions.UserNotFoundException;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Enrollment;
import com.example.LMS_sb.models.Student;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.repository.CourseRepository;
import com.example.LMS_sb.repository.EnrollmentRepository;
import com.example.LMS_sb.repository.StudentRepository;
import com.example.LMS_sb.repository.TeacherRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    public final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    public List<GetCourseDto> getAllCourses(){
        return courseRepository.findAll().stream().map(
                course -> new GetCourseDto(
                        course.getTitle(),
                        course.getDescription(),
                        course.getTeacher().getUser().getName(),
                        course.getCreatedAt()
                )
        ).toList();
    }

    public GetCourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return new GetCourseDto(
                course.getTitle(),
                course.getDescription(),
                course.getTeacher().getUser().getName(),
                course.getCreatedAt()
        );
    }

    public void updateCourseById(Long id, CourseDto dto) {
         Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
         Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(UserNotFoundException::new);
         course.setTitle(dto.getTitle());
         course.setDescription(dto.getDescription());
         course.setTeacher(teacher);
         courseRepository.save(course);

    }

    public void createCourse(CourseDto dto) {
        Course course = new Course();
        Teacher teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(UserNotFoundException::new);
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setTeacher(teacher);
        courseRepository.save(course);
    }

    public void deleteCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(CourseNotFoundException::new);
        courseRepository.delete(course);
    }

    public List<GetCourseDto> getAllMyCourses(Authentication authentication) {
        Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        List<Enrollment> enrollments=  enrollmentRepository.findAllByStudentId(student.getId());
        return enrollments.stream().map(
                enrollment ->
                        new GetCourseDto(
                             enrollment.getCourse().getTitle(),
                                enrollment.getCourse().getDescription(),
                                enrollment.getCourse().getTeacher().getUser().getName(),
                                enrollment.getCourse().getCreatedAt()

                )
        ).toList();
    }
}
