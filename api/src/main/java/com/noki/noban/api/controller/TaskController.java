package com.noki.noban.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.dto.request.TaskChangeAssignedToRequest;
import com.noki.noban.api.dto.request.TaskChangeStageRequest;
import com.noki.noban.api.dto.request.TaskRequest;
import com.noki.noban.api.dto.request.TaskUpdateRequest;
import com.noki.noban.api.dto.response.TaskResponse;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.TaskService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController 
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {
    
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@RequestBody @Valid TaskRequest taskRequest,
                           @PathVariable ("projectId") String projectId) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskResponse response = taskService.createTask(taskRequest, projectId, user.getUser());
        
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{taskId}/stage")
    public ResponseEntity<TaskResponse> changeStageTask(@RequestBody @Valid TaskChangeStageRequest taskRequest,
                           @PathVariable ("projectId") String projectId, 
                           @PathVariable ("taskId") String taskId) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskService.changeStage(taskRequest, projectId, taskId, user.getUser());
        
        return ResponseEntity.ok().build();
    }
    
    @PutMapping("/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(@PathVariable("taskId") String taskId, 
                            @PathVariable("projectId") String projectId,
                            @RequestBody @Valid TaskUpdateRequest taskUpdateRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        TaskResponse response = taskService.updateTaks(projectId, taskId, user.getUser(), taskUpdateRequest);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{taskId}/assigned")
    public ResponseEntity<Void> changeAssignedToTask(
            @PathVariable("projectId") String projectId,
            @PathVariable("taskId") String taskId,
            @RequestBody @Valid TaskChangeAssignedToRequest taskRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskService.changeAssignedTo(projectId, taskId, user.getUser(), taskRequest);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable("projectId") String projectId,
            @PathVariable("taskId") String taskId) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        taskService.deleteTask(projectId, taskId, user.getUser());

        return ResponseEntity.noContent().build();
    }
}
