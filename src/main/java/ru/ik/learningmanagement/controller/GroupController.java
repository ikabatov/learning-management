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
import ru.ik.learningmanagement.dto.GroupRequest;
import ru.ik.learningmanagement.dto.GroupResponse;
import ru.ik.learningmanagement.service.GroupService;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@Tag(name = "Groups", description = "Управление группами")
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить группу")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "409", description = "Группа с таким названием уже существует")
    public GroupResponse createGroup(@Valid @RequestBody GroupRequest groupRequest) {
        return groupService.createGroup(groupRequest);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить группу")
    @ApiResponse(responseCode = "400", description = "Некорректные данные")
    @ApiResponse(responseCode = "404", description = "Группа с таким ID не найдена")
    @ApiResponse(responseCode = "409", description = "Группа с таким названием уже существует")
    public GroupResponse updateGroup(@PathVariable Long id,
                                     @Valid @RequestBody GroupRequest groupRequest) {
        return groupService.updateGroup(id, groupRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить группу")
    @ApiResponse(responseCode = "404", description = "Группа с таким ID не найдена")
    public void deleteGroup(@PathVariable Long id) {
        groupService.deleteGroup(id);
    }
}
