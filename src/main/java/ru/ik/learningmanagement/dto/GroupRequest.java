package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record GroupRequest(
        @NotBlank(message = "Имя группы не может быть пустым")
        @Size(max = 100, message = "Имя группы не может превышать 100 символов")
        String name
) {
}
