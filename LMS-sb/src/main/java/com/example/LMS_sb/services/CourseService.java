package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.CourseDto;
import com.example.LMS_sb.exceptions.CourseNotFoundException;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.repository.CourseRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CourseService {
    public final CourseRepository courseRepository;

    public List<CourseDto> getAllCourses(){
        return courseRepository.findAll().stream().map(
                course -> new CourseDto(
                        course.getTitle(),
                        course.getDescription(),
                        course.getTeacher().getUser().getName()
                )
        ).toList();
    }

    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(CourseNotFoundException::new);
        return new CourseDto(
                course.getTitle(),
                course.getDescription(),
                course.getTeacher().getUser().getName()
        );
    }
}
