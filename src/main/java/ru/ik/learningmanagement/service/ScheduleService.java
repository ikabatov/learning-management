package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.ScheduleRequest;
import ru.ik.learningmanagement.dto.ScheduleResponse;
import ru.ik.learningmanagement.entity.Course;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.entity.Schedule;
import ru.ik.learningmanagement.exception.BadRequestException;
import ru.ik.learningmanagement.mapper.ScheduleMapper;
import ru.ik.learningmanagement.repository.CourseRepository;
import ru.ik.learningmanagement.repository.GroupRepository;
import ru.ik.learningmanagement.repository.ScheduleRepository;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final ScheduleMapper scheduleMapper;

    @Transactional
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        Group group = groupRepository.findByIdOrThrow(scheduleRequest.groupId());
        Course course = courseRepository.findByIdOrThrow(scheduleRequest.courseId());

        if (scheduleRequest.dateStart().isAfter(scheduleRequest.dateEnd())
                || scheduleRequest.dateStart().isEqual(scheduleRequest.dateEnd())) {
            throw new BadRequestException("Дата окончания должна быть позже даты начала");
        }

        if(!group.getCourses().contains(course)) {
            throw new BadRequestException("Группа не записана на этот курс");
        }

        Schedule schedule = scheduleMapper.toEntity(scheduleRequest);
        schedule.setGroup(group);
        schedule.setCourse(course);
        scheduleRepository.save(schedule);
        return scheduleMapper.toDto(schedule);
    }
}
