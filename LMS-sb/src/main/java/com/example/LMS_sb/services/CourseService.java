package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.CourseDto;
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
}
