package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.ik.learningmanagement.dto.TeacherResponse;
import ru.ik.learningmanagement.entity.Teacher;
import ru.ik.learningmanagement.mapper.TeacherMapper;
import ru.ik.learningmanagement.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Учитель с id " + id + " не найден"));
        return teacherMapper.toDto(teacher);
    }
}
