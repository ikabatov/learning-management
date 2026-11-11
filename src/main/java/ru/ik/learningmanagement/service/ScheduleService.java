package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.ScheduleRequest;
import ru.ik.learningmanagement.dto.ScheduleResponse;
import ru.ik.learningmanagement.dto.ScheduleTimeRequest;
import ru.ik.learningmanagement.entity.Course;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.entity.Schedule;
import ru.ik.learningmanagement.exception.BadRequestException;
import ru.ik.learningmanagement.mapper.ScheduleMapper;
import ru.ik.learningmanagement.repository.CourseRepository;
import ru.ik.learningmanagement.repository.GroupRepository;
import ru.ik.learningmanagement.repository.ScheduleRepository;
import ru.ik.learningmanagement.repository.TeacherRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        Group group = groupRepository.findByIdOrThrow(scheduleRequest.groupId());
        Course course = courseRepository.findByIdOrThrow(scheduleRequest.courseId());
        validateDateRange(scheduleRequest.dateStart(), scheduleRequest.dateEnd());

        if (!group.getCourses().contains(course)) {
            throw new BadRequestException("Группа не записана на этот курс");
        }

        Schedule schedule = scheduleMapper.toEntity(scheduleRequest);
        schedule.setGroup(group);
        schedule.setCourse(course);
        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public ScheduleResponse updateSchedule(Long id, ScheduleTimeRequest scheduleTimeRequest) {
        Schedule schedule = scheduleRepository.findByIdOrThrow(id);
        validateDateRange(scheduleTimeRequest.dateStart(), scheduleTimeRequest.dateEnd());
        scheduleMapper.updateEntity(scheduleTimeRequest, schedule);
        return scheduleMapper.toDto(schedule);
    }

    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findByIdOrThrow(id);
        scheduleRepository.delete(schedule);
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponse> getGroupSchedule(Long groupId, Pageable pageable) {
        groupRepository.findByIdOrThrow(groupId);
        Page<Schedule> schedulePage = scheduleRepository.findByGroupId(groupId, pageable);
        Page<ScheduleResponse> responsePage = schedulePage.map(schedule -> scheduleMapper.toDto(schedule));
        return responsePage;
    }

    @Transactional(readOnly = true)
    public Page<ScheduleResponse> getTeacherSchedule(Long teacherId, Pageable pageable) {
        teacherRepository.findByIdOrThrow(teacherId);
        Page<Schedule> schedulePage = scheduleRepository.findByCourseTeacherId(teacherId, pageable);
        Page<ScheduleResponse> responsePage = schedulePage.map(schedule -> scheduleMapper.toDto(schedule));
        return responsePage;
    }

    private void validateDateRange(LocalDateTime dateStart, LocalDateTime dateEnd) {
        if (dateStart.isAfter(dateEnd)
                || dateStart.isEqual(dateEnd)) {
            throw new BadRequestException("Дата окончания должна быть позже даты начала");
        }
    }
}
