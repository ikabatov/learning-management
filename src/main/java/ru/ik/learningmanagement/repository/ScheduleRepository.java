package ru.ik.learningmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ik.learningmanagement.entity.Schedule;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
