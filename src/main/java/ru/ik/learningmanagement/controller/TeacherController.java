package ru.ik.learningmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
import ru.ik.learningmanagement.dto.TeacherRequest;
import ru.ik.learningmanagement.dto.TeacherResponse;
import ru.ik.learningmanagement.service.TeacherService;

@RestController
@RequestMapping("/api/v1/teachers")
@RequiredArgsConstructor
@Tag(name = "Teachers", description = "Управление учителями")
public class TeacherController {

    private final TeacherService teacherService;

    @GetMapping("/{id}")
    @Operation(summary = "Получить учителя по id")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    public TeacherResponse getTeacherById(@PathVariable Long id) {
        return teacherService.getTeacherById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить учителя")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public TeacherResponse createTeacher(@Valid @RequestBody TeacherRequest teacherRequest) {
        return teacherService.createTeacher(teacherRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить данные учителя")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    public TeacherResponse updateTeacher(@PathVariable Long id,
                                         @Valid @RequestBody TeacherRequest teacherRequest) {
        return teacherService.updateTeacher(id, teacherRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить информацию об учителе")
    @ApiResponse(responseCode = "404", description = "Учитель не найден")
    public void deleteTeacher(@PathVariable Long id) {
        teacherService.deleteTeacher(id);
    }
}
