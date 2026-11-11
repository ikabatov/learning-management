package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ScheduleTimeRequest(
        @NotNull(message = "Дата старта не может быть пустой")
        LocalDateTime dateStart,
        @NotNull(message = "Дата окончания не может быть пустой")
        LocalDateTime dateEnd
) {

}
