package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
