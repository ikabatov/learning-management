package ru.ik.learningmanagement.dto;

import java.time.LocalDateTime;

public record CourseResponse(
        Long id,
        String name,
        String description,
        TeacherResponse teacher,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
