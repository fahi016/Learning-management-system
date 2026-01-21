package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.CourseDto;
import com.example.LMS_sb.dtos.GetStudentCourseDto;
import com.example.LMS_sb.dtos.GetTeacherCourseDto;
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
    private final TeacherService teacherService;

    public List<GetStudentCourseDto> getAllCourses(){
        return courseRepository.findAll().stream().map(
                course -> new GetStudentCourseDto(
                        course.getId(),
                        course.getTitle(),
                        course.getDescription(),
                        course.getTeacher().getUser().getName(),
                        course.getCreatedAt()
                )
        ).toList();
    }

    public GetStudentCourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return new GetStudentCourseDto(
                course.getId(),
                course.getTitle(),
                course.getDescription(),
                course.getTeacher().getUser().getName(),
                course.getCreatedAt()
        );
    }

    public Course findCourseById(Long courseId){
        return courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
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

    public List<GetStudentCourseDto> getStudentCourses(Authentication authentication) {
        Student student = studentRepository.findByUserEmail(authentication.getName()).orElseThrow(UserNotFoundException::new);
        List<Enrollment> enrollments=  enrollmentRepository.findAllByStudentId(student.getId());
        return enrollments.stream().map(
                enrollment ->
                        new GetStudentCourseDto(
                                enrollment.getCourse().getId(),
                                enrollment.getCourse().getTitle(),
                                enrollment.getCourse().getDescription(),
                                enrollment.getCourse().getTeacher().getUser().getName(),
                                enrollment.getCourse().getCreatedAt()

                )
        ).toList();
    }

    public List<GetTeacherCourseDto> getTeacherCourses(Authentication authentication) {
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        List<Course> courses = courseRepository.findAllByTeacher(teacher);
        return courses.stream()
                .map(
                        course -> new GetTeacherCourseDto(
                                course.getId(),
                                course.getTitle(),
                                course.getDescription()

                        )
                ).toList();

    }


    public List<Course> getCoursesByTeacher(Teacher teacher) {
        return courseRepository.findAllByTeacher(teacher);
    }
}
