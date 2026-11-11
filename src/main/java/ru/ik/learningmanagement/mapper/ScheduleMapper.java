package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.ik.learningmanagement.dto.ScheduleRequest;
import ru.ik.learningmanagement.dto.ScheduleResponse;
import ru.ik.learningmanagement.entity.Schedule;

@Mapper(componentModel = "spring", uses = {GroupMapper.class, CourseMapper.class})
public interface ScheduleMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    @Mapping(target = "course", ignore = true)
    Schedule toEntity(ScheduleRequest scheduleRequest);

    ScheduleResponse toDto(Schedule schedule);
}
