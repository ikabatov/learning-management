package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ik.learningmanagement.dto.CourseRequest;
import ru.ik.learningmanagement.dto.CourseResponse;
import ru.ik.learningmanagement.entity.Course;

@Mapper(componentModel = "spring", uses = TeacherMapper.class)
public interface CourseMapper {

    CourseResponse toDto(Course course);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "groups", ignore = true)
    Course toEntity(CourseRequest courseRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "groups", ignore = true)
    void updateEntity(CourseRequest courseRequest, @MappingTarget Course course);
}
