package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Student;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface StudentRepository extends JpaRepository<Student, Long> {

    default Student findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Студент с id: " + id + " не найден"));
    }
}
