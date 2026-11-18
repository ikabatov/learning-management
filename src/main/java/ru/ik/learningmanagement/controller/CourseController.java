package ru.ik.learningmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ik.learningmanagement.dto.CourseRequest;
import ru.ik.learningmanagement.dto.CourseResponse;
import ru.ik.learningmanagement.service.CourseService;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
@Tag(name = "Courses", description = "Управление курсами")
public class CourseController {

    private final CourseService courseService;

    @GetMapping
    @Operation(summary = "Найти все курсы")
    public Page<CourseResponse> getAllCourses(
            @ParameterObject @PageableDefault(size = 10, sort = "id") Pageable pageable) {
        return courseService.getAllCourses(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Найти курс по ID")
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    public CourseResponse getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить курс")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    public CourseResponse createCourse(@Valid @RequestBody CourseRequest courseRequest) {
        return courseService.createCourse(courseRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить курс")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "404", description = "Курс или учитель не найден")
    public CourseResponse updateCourse(@PathVariable Long id,
                                       @Valid @RequestBody CourseRequest courseRequest) {
        return courseService.updateCourse(id, courseRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить курс")
    @ApiResponse(responseCode = "404", description = "Курс не найден")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

    @PostMapping("/{courseId}/groups/{groupId}")
    @Operation(summary = "Добавить группу на курс")
    @ApiResponse(responseCode = "404", description = "Курс или группа не найден")
    public void addGroupToCourse(@PathVariable Long courseId, @PathVariable Long groupId) {
        courseService.addGroupToCourse(courseId, groupId);
    }
}
