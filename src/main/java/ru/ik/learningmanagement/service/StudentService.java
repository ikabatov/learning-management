package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.StudentRequest;
import ru.ik.learningmanagement.dto.StudentResponse;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.entity.Student;
import ru.ik.learningmanagement.exception.NotFoundException;
import ru.ik.learningmanagement.mapper.StudentMapper;
import ru.ik.learningmanagement.repository.GroupRepository;
import ru.ik.learningmanagement.repository.StudentRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentResponse createStudent(StudentRequest studentRequest) {
        Set<Group> groups = findGroupsOrThrow(studentRequest.groupIds());
        Student student = studentMapper.toEntity(studentRequest);
        student.setGroups(groups);
        studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        Student student = studentRepository.findByIdOrThrow(id);
        Set<Group> groups = findGroupsOrThrow(studentRequest.groupIds());
        student.setGroups(groups);
        studentMapper.updateEntity(studentRequest, student);
        studentRepository.save(student);
        return studentMapper.toDto(student);
    }

    @Transactional
    public void deleteStudent(Long id) {
        Student student = studentRepository.findByIdOrThrow(id);
        studentRepository.delete(student);
    }

    private Set<Group> findGroupsOrThrow(Set<Long> groupIds) {
        List<Group> groups = groupRepository.findAllById(groupIds);

        Set<Long> foundIds = groups.stream()
                .map(Group::getId)
                .collect(Collectors.toSet());
        Set<Long> missingIds = groupIds.stream()
                .filter(id -> !foundIds.contains(id))
                .collect(Collectors.toSet());

        if (!missingIds.isEmpty()) {
            throw new NotFoundException("Группы с id " + missingIds + " не найдены");
        }
        return new HashSet<>(groups);
    }
}
