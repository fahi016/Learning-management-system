package com.example.LMS_sb.repository;

import com.example.LMS_sb.models.Course;
import com.example.LMS_sb.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LectureRepository extends JpaRepository<Lecture,Long> {

    List<Lecture> findAllByCourse(Course course);

    Optional<Lecture> findByIdAndCourse(Long lectureId, Course course);

}
