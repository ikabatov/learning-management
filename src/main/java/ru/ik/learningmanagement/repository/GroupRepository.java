package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
