package ru.ik.learningmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.ik.learningmanagement.dto.StudentRequest;
import ru.ik.learningmanagement.dto.StudentResponse;
import ru.ik.learningmanagement.service.StudentService;

@RestController
@RequestMapping("/api/v1/students")
@RequiredArgsConstructor
@Tag(name = "Students", description = "Управление студентами")
public class StudentController {

    private final StudentService studentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить студента")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "404", description = "Группы с таким ID не найдены")
    public StudentResponse createStudent(@Valid @RequestBody StudentRequest studentRequest) {
        return studentService.createStudent(studentRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить студента")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "404", description = "Студент или группы с таким ID не найдены")
    public StudentResponse updateStudent(@PathVariable Long id,
                                         @Valid @RequestBody StudentRequest studentRequest) {
        return studentService.updateStudent(id, studentRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить студента")
    @ApiResponse(responseCode = "404", description = "Студент с таким ID не найден")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
