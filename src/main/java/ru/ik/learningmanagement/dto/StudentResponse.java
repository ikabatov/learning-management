package ru.ik.learningmanagement.dto;

import java.util.Set;

public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        Set<GroupResponse> groups
) {
}
