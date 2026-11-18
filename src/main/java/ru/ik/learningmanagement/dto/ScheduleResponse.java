package ru.ik.learningmanagement.dto;

import java.time.LocalDateTime;

public record ScheduleResponse(
        Long id,
        GroupResponse group,
        CourseResponse course,
        LocalDateTime dateStart,
        LocalDateTime dateEnd
) {

}
