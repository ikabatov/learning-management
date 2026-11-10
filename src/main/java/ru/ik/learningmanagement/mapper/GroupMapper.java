package ru.ik.learningmanagement.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ik.learningmanagement.dto.GroupRequest;
import ru.ik.learningmanagement.dto.GroupResponse;
import ru.ik.learningmanagement.entity.Group;

@Mapper(componentModel = "spring")
public interface GroupMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "courses", ignore = true)
    Group toEntity(GroupRequest groupRequest);

    GroupResponse toDto(Group group);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "students", ignore = true)
    @Mapping(target = "courses", ignore = true)
    void updateEntity(GroupRequest groupRequest, @MappingTarget Group group);
}
