package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Schedule;
import ru.ik.learningmanagement.exception.NotFoundException;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Расписание с ID: " + id + " не найдено"));
    }
}
