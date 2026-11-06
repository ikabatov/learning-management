package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
