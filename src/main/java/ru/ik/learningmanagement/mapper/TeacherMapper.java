package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ik.learningmanagement.dto.TeacherRequest;
import ru.ik.learningmanagement.dto.TeacherResponse;
import ru.ik.learningmanagement.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherResponse toDto(Teacher teacher);

    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequest teacherRequest);

    @Mapping(target = "id", ignore = true)
    void updateEntity(TeacherRequest teacherRequest, @MappingTarget Teacher teacher);
}
