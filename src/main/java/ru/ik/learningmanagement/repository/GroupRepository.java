package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface GroupRepository extends JpaRepository<Group, Long> {

    boolean existsByName(String name);

    boolean existsByNameAndIdNot(String name, Long id);

    default Group findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Группа с ID: " + id + " не найдена"));
    }
}
