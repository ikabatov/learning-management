package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Course;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface CourseRepository extends JpaRepository<Course, Long> {

    default Course findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Курс с id " + id + " не найден"));
    }
}
