package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TeacherRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 100, message = "Имя не может превышать 100 символов")
        String firstName,
        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(max = 100, message = "Фамилия не может превышать 100 символов")
        String lastName
) {
}
