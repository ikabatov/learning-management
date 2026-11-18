package ru.ik.learningmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import ru.ik.learningmanagement.repository.ScheduleRepository;
import ru.ik.learningmanagement.repository.TeacherRepository;

@Service
@RequiredArgsConstructor
public class CourseService {

    private final TeacherRepository teacherRepository;
    private final CourseMapper courseMapper;
    private final CourseRepository courseRepository;
    private final GroupRepository groupRepository;
    private final ScheduleRepository scheduleRepository;

    @Transactional(readOnly = true)
    public CourseResponse getCourseById(Long id) {
        Course course = courseRepository.findByIdOrThrow(id);
        return courseMapper.toDto(course);
    }

    @Transactional(readOnly = true)
    public Page<CourseResponse> getAllCourses(Pageable pageable) {
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.map(courseMapper::toDto);
    }

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
        courseRepository.saveAndFlush(course);
        return courseMapper.toDto(course);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Course course = courseRepository.findByIdOrThrow(id);
        scheduleRepository.deleteAllByCourseId(id);
        for (Group group : course.getGroups()) {
            group.getCourses().remove(course);
        }
        courseRepository.delete(course);
    }

    @Transactional
    public void addGroupToCourse(Long courseId, Long groupId) {
        Course course = courseRepository.findByIdOrThrow(courseId);
        Group group = groupRepository.findByIdOrThrow(groupId);
        group.getCourses().add(course);
    }
}
