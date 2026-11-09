package ru.ik.learningmanagement.dto;

public record CourseResponse(
        Long id,
        String name,
        String description,
        TeacherResponse teacher
) {
}
