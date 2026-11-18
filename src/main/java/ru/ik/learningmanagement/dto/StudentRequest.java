package ru.ik.learningmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record StudentRequest(
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 100, message = "Имя не может превышать 100 символов")
        String firstName,
        @NotBlank(message = "Фамилия не может быть пустой")
        @Size(max = 100, message = "Фамилия не может превышать 100 символов")
        String lastName,
        @NotEmpty(message = "Студент должен состоять хотя бы в одной группе")
        Set<@NotNull(message = "id группы не может быть null") Long> groupIds
) {
}
