package com.example.LMS_sb.services;


import com.example.LMS_sb.dtos.AddLectureDto;
import com.example.LMS_sb.exceptions.CourseExcpetion;
import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Lecture;
import com.example.LMS_sb.models.Teacher;
import com.example.LMS_sb.repository.LectureRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LectureService {

    private TeacherService teacherService;
    private CourseService courseService;
    private LectureRepository lectureRepository;

    public void addLecture(Long courseId, Authentication authentication, AddLectureDto dto){
        Teacher teacher = teacherService.getTeacherByUserEmail(authentication.getName());
        Course course = courseService.findCourseById(courseId);
        if (course.getTeacher().getId() != teacher.getId()) {
            throw new CourseExcpetion("Teacher is not assigned to this course");
        }
        Lecture lecture = new Lecture();
        lecture.setCourse(course);
        lecture.setTitle(dto.getTitle());
        lecture.setVideoUrl(dto.getVideoUrl());
        lecture.setNotesUrl(dto.getNotesUrl());
        lectureRepository.save(lecture);



    }

    public List<LectureDto> viewLectures(Authentication authentication) {
    }
}
