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
import ru.ik.learningmanagement.dto.ScheduleRequest;
import ru.ik.learningmanagement.dto.ScheduleResponse;
import ru.ik.learningmanagement.dto.ScheduleTimeRequest;
import ru.ik.learningmanagement.service.ScheduleService;

@RestController
@RequestMapping("/api/v1/schedules")
@RequiredArgsConstructor
@Tag(name = "Schedules", description = "Управление расписанием")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить расписание")
    @ApiResponse(responseCode = "404", description = "Группы или курс с таким ID не найдены")
    @ApiResponse(responseCode = "400", description = "Некорректные данные или дата окончания не позже даты начала")
    public ScheduleResponse createSchedule(@Valid @RequestBody ScheduleRequest scheduleRequest) {
        return scheduleService.createSchedule(scheduleRequest);
    }

    @GetMapping("/group/{groupId}")
    @Operation(summary = "Получить расписание группы")
    @ApiResponse(responseCode = "404", description = "Группа с таким ID не найдена")
    public Page<ScheduleResponse> getGroupSchedule(
            @PathVariable Long groupId,
            @ParameterObject @PageableDefault(size = 10, sort = "dateStart") Pageable pageable) {
        return scheduleService.getGroupSchedule(groupId, pageable);
    }

    @GetMapping("/teacher/{teacherId}")
    @Operation(summary = "Получить расписание учителя")
    @ApiResponse(responseCode = "404", description = "Учитель с таким ID не найден")
    public Page<ScheduleResponse> getTeacherSchedule(
            @PathVariable Long teacherId,
            @ParameterObject @PageableDefault(size = 10, sort = "dateStart") Pageable pageable) {
        return scheduleService.getTeacherSchedule(teacherId, pageable);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить время расписания")
    @ApiResponse(responseCode = "400", description = "Некорректные данные или дата окончания не позже даты начала")
    @ApiResponse(responseCode = "404", description = "Расписание с таким ID не найдено")
    public ScheduleResponse updateSchedule(@PathVariable Long id,
                                           @Valid @RequestBody ScheduleTimeRequest scheduleTimeRequest) {
        return scheduleService.updateSchedule(id, scheduleTimeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить расписание")
    @ApiResponse(responseCode = "404", description = "Расписание с таким ID не найдено")
    public void deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
    }
}
