package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleRequest(
        @NotNull(message = "ID группы не может быть пустой")
        Long groupId,
        @NotNull(message = "ID курса не может быть пустым")
        Long courseId,
        @NotNull(message = "Дата старта не может быть пустой")
        LocalDateTime dateStart,
        @NotNull(message = "Дата окончания не может быть пустой")
        LocalDateTime dateEnd
) {

}
