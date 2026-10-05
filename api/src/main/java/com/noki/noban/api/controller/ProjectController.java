package com.noki.noban.api.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.dto.request.ProjectRequest;
import com.noki.noban.api.dto.response.UserProjectsResponse;
import com.noki.noban.api.dto.response.ProjectResponse;
import com.noki.noban.api.dto.response.ProjectUsersResponse;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.ProjectService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private ProjectService projectService;

    public ProjectController(ProjectService projectService){
        this.projectService = projectService;
    }
    
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@RequestBody @Valid ProjectRequest request){

        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        ProjectResponse response = projectService.createProject(request, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/my")
    public ResponseEntity<UserProjectsResponse> listMyProjects(
        @RequestParam(name = "size", defaultValue = "10") 
        @Min(value = 1, message = "Size must be greater than 0")
        @Max(value = 100, message = "Size must be less than or equal to 100")
        Integer size, 

        @RequestParam(name = "page", defaultValue = "0") 
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        Integer page,

        @RequestParam(name = "direction", defaultValue = "ASC") 
        Direction direction
    ) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Sort sort = Sort.by(direction, "name");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return ResponseEntity.status(HttpStatus.OK).body(projectService.listMyProjects(userDetails.getUser(), pageRequest));
    }
    
    @GetMapping(path = "/{id}")
    public ResponseEntity<ProjectUsersResponse> listProjectUsers(
        @PathVariable(name = "id") String id,

        @RequestParam(name = "size", defaultValue = "10") 
        @Min(value = 1, message = "Size must be greater than 0")
        @Max(value = 100, message = "Size must be less than or equal to 100")
        Integer size, 

        @RequestParam(name = "page", defaultValue = "0") 
        @Min(value = 0, message = "Page must be greater than or equal to 0")
        Integer page,

        @RequestParam(name = "direction", defaultValue = "ASC") 
        Direction direction
    ){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Sort sort = Sort.by(direction, "u.name");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(projectService.listProjectUsers(id, pageRequest, userDetails.getUser()));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
        @PathVariable(name = "id") String id,
        @RequestBody @Valid ProjectRequest request
    ){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); 
        
        return ResponseEntity.ok(projectService.updateProject(id, request, userDetails.getUser()));
    }

    @DeleteMapping (path = "/{id}")
    public ResponseEntity<Void> deleteProject(
        @PathVariable(name = "id") String id
    ){
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        projectService.deleteProject(id, userDetails.getUser());

        return ResponseEntity.noContent().build();
    }
}