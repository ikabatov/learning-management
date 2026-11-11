package ru.ik.learningmanagement.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ik.learningmanagement.entity.Schedule;
import ru.ik.learningmanagement.exception.NotFoundException;

import java.time.LocalDateTime;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    default Schedule findByIdOrThrow(Long id) {
        return findById(id).orElseThrow(
                () -> new NotFoundException("Расписание с ID: " + id + " не найдено"));
    }

    Page<Schedule> findByGroupId(Long groupId, Pageable pageable);

    Page<Schedule> findByCourseTeacherId(Long teacherId, Pageable pageable);

    @Modifying
    @Query("delete from Schedule s where s.dateEnd < :date")
    void deleteAllEndedBefore(@Param("date") LocalDateTime date);
}
