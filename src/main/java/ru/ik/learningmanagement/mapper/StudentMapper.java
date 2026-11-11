package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ik.learningmanagement.dto.StudentRequest;
import ru.ik.learningmanagement.dto.StudentResponse;
import ru.ik.learningmanagement.entity.Student;

@Mapper(componentModel = "spring", uses = GroupMapper.class)
public interface StudentMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    Student toEntity(StudentRequest studentRequest);

    StudentResponse toDto(Student student);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "groups", ignore = true)
    void updateEntity(StudentRequest studentRequest, @MappingTarget Student student);
}
