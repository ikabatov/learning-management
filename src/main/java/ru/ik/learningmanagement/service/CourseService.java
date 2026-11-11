package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.ik.learningmanagement.dto.CourseRequest;
import ru.ik.learningmanagement.dto.CourseResponse;
import ru.ik.learningmanagement.entity.Course;
import ru.ik.learningmanagement.entity.Group;
import ru.ik.learningmanagement.entity.Teacher;
import ru.ik.learningmanagement.mapper.CourseMapper;
import ru.ik.learningmanagement.repository.CourseRepository;
import ru.ik.learningmanagement.repository.GroupRepository;
import ru.ik.learningmanagement.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;

    public CourseResponse createCourse(CourseRequest courseRequest) {
        Teacher teacher = teacherRepository.findByIdOrThrow(courseRequest.teacherId());
        Course course = courseMapper.toEntity(courseRequest);
        course.setTeacher(teacher);
        Course createdCourse = courseRepository.save(course);
        return courseMapper.toDto(createdCourse);
    }

    @Transactional
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        Course course = courseRepository.findByIdOrThrow(id);
        Teacher teacher = teacherRepository.findByIdOrThrow(courseRequest.teacherId());
        courseMapper.updateEntity(courseRequest, course);
        course.setTeacher(teacher);
        courseRepository.save(course);
        return courseMapper.toDto(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findByIdOrThrow(id);
        courseRepository.delete(course);
    }

    @Transactional
    public void addGroupToCourse(Long courseId, Long groupId) {
        Course course = courseRepository.findByIdOrThrow(courseId);
        Group group = groupRepository.findByIdOrThrow(groupId);
        group.getCourses().add(course);
    }
}
