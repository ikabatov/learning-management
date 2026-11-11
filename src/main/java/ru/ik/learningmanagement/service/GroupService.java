package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.GroupRequest;
import ru.ik.learningmanagement.dto.GroupResponse;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.exception.AlreadyExistsException;
import ru.ik.learningmanagement.mapper.GroupMapper;
import ru.ik.learningmanagement.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupResponse createGroup(GroupRequest groupRequest) {
        validateNameIsUnique(groupRequest.name());
        Group group = groupMapper.toEntity(groupRequest);
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    @Transactional
    public GroupResponse updateGroup(Long id, GroupRequest groupRequest) {
        Group group = groupRepository.findByIdOrThrow(id);
        validateNameIsUniqueExcept(groupRequest.name(), id);
        groupMapper.updateEntity(groupRequest, group);
        groupRepository.save(group);
        return groupMapper.toDto(group);
    }

    @Transactional
    public void deleteGroup(Long id) {
        Group group = groupRepository.findByIdOrThrow(id);
        groupRepository.delete(group);
    }

    private void validateNameIsUnique(String name) {
        if (groupRepository.existsByName(name)) {
            throw new AlreadyExistsException("Группа " + name + " уже существует");
        }
    }

    private void validateNameIsUniqueExcept(String name, Long id) {
        if (groupRepository.existsByNameAndIdNot(name, id)) {
            throw new AlreadyExistsException("Группа " + name + " уже существует");
        }
    }
}
