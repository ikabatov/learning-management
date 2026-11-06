package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
