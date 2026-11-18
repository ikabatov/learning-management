package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CourseRequest(
        @NotBlank(message = "Название курса не может быть пустым")
        @Size(max = 100, message = "Название курса не может превышать 100 символов")
        String name,
        String description,
        @NotNull(message = "Не указан учитель курса")
        Long teacherId
) {
}
