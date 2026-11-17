package ru.ik.learningmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Course;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @EntityGraph(attributePaths = "teacher")
    Page<Course> findAll(Pageable pageable);

    default Course findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Курс с id " + id + " не найден"));
    }
}
