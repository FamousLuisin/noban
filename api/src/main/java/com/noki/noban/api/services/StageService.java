package com.noki.noban.api.services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.request.StageOrderRequest;
import com.noki.noban.api.dto.request.StageRequest;
import com.noki.noban.api.exceptions.ResourceNotFoundException;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.StageModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.ContributorsModel.RoleType;
import com.noki.noban.api.repository.ProjectRepository;
import com.noki.noban.api.repository.StageRepository;

import jakarta.transaction.Transactional;

@Service 
public class StageService {

    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final ProjectService projectService;

    public StageService(ProjectRepository projectRepository, StageRepository stageRepository, ProjectService projectService) {
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.projectService = projectService;
    }

    @Transactional 
	public void createStage(StageRequest stageRequest, String projectId, UserModel user) {
		UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        stageRepository.findFirstByProjectIdOrderByPositionDesc(projectUUID)
                .ifPresentOrElse(
                    lastStage -> {
                        StageModel newStage = new StageModel(stageRequest.name(), lastStage.getPosition() + 1, project);
                        stageRepository.save(newStage);
                    },
                    () -> {
                        StageModel newStage = new StageModel(stageRequest.name(), 1, project);
                        stageRepository.save(newStage);
                    }
                );
	}

    @Transactional 
    public void changeOrderStages(StageOrderRequest stageOrderRequest, String projectId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findByIdWithStages(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        List<StageModel> stages = project.getStages();
        List<Integer> stageIds = stageOrderRequest.stageIds();

        if (new HashSet<>(stageIds).size() != stageIds.size()) {
            throw new IllegalArgumentException("Duplicate stage IDs are not allowed in the request.");
        }

        if (new HashSet<>(stageIds).size() != stages.size()) {
            throw new IllegalArgumentException("The number of stage IDs provided does not match the number of stages in the project.");
        }

        Map<Integer, StageModel> stageMap = stages.stream()
                .collect(Collectors.toMap(StageModel::getId, stage -> stage));

        for (int i = 0; i < stageIds.size(); i++) {
            Integer stageId = stageIds.get(i);

            StageModel stage = stageMap.get(stageId);
            
            if (stage == null) {
                throw new ResourceNotFoundException("Stage with ID " + stageId + " not found in the project.");
            }
            stage.setPosition(i + 1);
        }
    }

    @Transactional 
    public void updateStage(String projectId, Integer stageId, StageRequest stageRequest, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        StageModel stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        if (stage.getProject() == null || !stage.getProject().getId().equals(projectUUID)) {
            throw new IllegalArgumentException("Stage does not belong to the specified project.");
        }
        
        stage.setName(stageRequest.name());

        stageRepository.save(stage);
    }

    @Transactional 
    public void deleteStage(String projectId, Integer stageId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        StageModel stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        if (stage.getProject() == null || !stage.getProject().getId().equals(projectUUID)) {
            throw new IllegalArgumentException("Stage does not belong to the specified project.");
        }

        stageRepository.delete(stage);
    }
    
}
