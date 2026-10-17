package com.noki.noban.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.dto.request.StageOrderRequest;
import com.noki.noban.api.dto.request.StageRequest;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.StageService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/projects/{projectId}/stages")
public class StageController {
    
    private final StageService stageService;

    public StageController(StageService stageService) {
        this.stageService = stageService;
    }

    @PostMapping
    public ResponseEntity<Void> createStage(@RequestBody @Valid StageRequest stageRequest,
                                            @PathVariable("projectId") String projectId) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        stageService.createStage(stageRequest, projectId, user.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping(path = "/order")
    public ResponseEntity<Void> changeOrderStages(
        @RequestBody StageOrderRequest stageOrderRequest,
        @PathVariable("projectId") String projectId
    ) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        stageService.changeOrderStages(stageOrderRequest, projectId, user.getUser());
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{stageId}")
    public ResponseEntity<Void> updateStage(
        @PathVariable("projectId") String projectId,
        @PathVariable("stageId") Integer stageId,
        @RequestBody @Valid StageRequest stageRequest
    ) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        stageService.updateStage(projectId, stageId, stageRequest, user.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{stageId}")
    public ResponseEntity<Void> deleteStage(
        @PathVariable("projectId") String projectId,
        @PathVariable("stageId") Integer stageId
    ) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        stageService.deleteStage(projectId, stageId, user.getUser());
        return ResponseEntity.noContent().build();
    }
}
