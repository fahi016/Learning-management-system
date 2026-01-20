package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.AddUpdateLectureDto;
import com.example.LMS_sb.dtos.LectureDto;
import com.example.LMS_sb.exceptions.CourseExcpetion;
import com.example.LMS_sb.exceptions.LectureNotFoundException;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Lecture;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.repository.LectureRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LectureService {

    private TeacherService teacherService;
    private CourseService courseService;
    private LectureRepository lectureRepository;

    public void addLecture(Long courseId, Authentication authentication, AddUpdateLectureDto dto){
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Course course = courseService.findCourseById(courseId);
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new CourseExcpetion("Teacher is not assigned to this course");
        }
        Lecture lecture = new Lecture();
        lecture.setCourse(course);
        lecture.setTitle(dto.getTitle());
        lecture.setVideoUrl(dto.getVideoUrl());
        lecture.setNotesUrl(dto.getNotesUrl());
        lectureRepository.save(lecture);



    }

    public List<LectureDto> viewLectures(Authentication authentication, Long courseId) {
        Course course = courseService.findCourseById(courseId);
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new CourseExcpetion("Teacher is not assigned to this course");
        }
        List<Lecture> lectures = lectureRepository.findAllByCourse(course);



        return lectures.stream()
                .map(lecture -> new LectureDto(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getVideoUrl(),
                        lecture.getNotesUrl()
                ))
                .toList();
    }

    public LectureDto viewLecture(Authentication authentication, Long courseId, Long lectureId) {

        Course course = courseService.findCourseById(courseId);
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        if (!course.getTeacher().getId().equals(teacher.getId())) {
            throw new CourseExcpetion("Teacher is not assigned to this course");
        }

        Lecture lecture = lectureRepository.findByIdAndCourse(lectureId,course).orElseThrow(LectureNotFoundException::new);

        return new LectureDto(
                        lecture.getId(),
                        lecture.getTitle(),
                        lecture.getVideoUrl(),
                        lecture.getNotesUrl()
                );
    }
}
