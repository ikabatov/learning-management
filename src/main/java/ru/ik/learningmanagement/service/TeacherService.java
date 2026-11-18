package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.TeacherRequest;
import ru.ik.learningmanagement.dto.TeacherResponse;
import ru.ik.learningmanagement.entity.Teacher;
import ru.ik.learningmanagement.mapper.TeacherMapper;
import ru.ik.learningmanagement.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    @Transactional(readOnly = true)
    public TeacherResponse getTeacherById(Long id) {
        Teacher teacher = teacherRepository.findByIdOrThrow(id);
        return teacherMapper.toDto(teacher);
    }

    public TeacherResponse createTeacher(TeacherRequest teacherRequest) {
        Teacher teacher = teacherMapper.toEntity(teacherRequest);
        Teacher createdTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(createdTeacher);
    }

    @Transactional
    public TeacherResponse updateTeacher(Long id, TeacherRequest teacherRequest) {
        Teacher teacher = teacherRepository.findByIdOrThrow(id);
        teacherMapper.updateEntity(teacherRequest, teacher);
        Teacher updatedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toDto(updatedTeacher);
    }

    @Transactional
    public void deleteTeacher(Long id) {
        Teacher teacher = teacherRepository.findByIdOrThrow(id);
        teacherRepository.delete(teacher);
    }
}
