package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import ru.ik.learningmanagement.dto.TeacherResponse;
import ru.ik.learningmanagement.entity.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherResponse toDto(Teacher teacher);

}
