package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Teacher;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

    default Teacher findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Учитель с id " + id + " не найден"));
    }
}
