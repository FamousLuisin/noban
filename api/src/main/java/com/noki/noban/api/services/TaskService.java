package com.noki.noban.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.request.TaskChangeAssignedToRequest;
import com.noki.noban.api.dto.request.TaskChangeStageRequest;
import com.noki.noban.api.dto.request.TaskRequest;
import com.noki.noban.api.dto.request.TaskUpdateRequest;
import com.noki.noban.api.dto.response.TaskResponse;
import com.noki.noban.api.exceptions.ResourceNotFoundException;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.StageModel;
import com.noki.noban.api.models.TaskModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.ContributorsModel.RoleType;
import com.noki.noban.api.repository.ContributorsRepository;
import com.noki.noban.api.repository.ProjectRepository;
import com.noki.noban.api.repository.StageRepository;
import com.noki.noban.api.repository.TaskRepository;

import jakarta.transaction.Transactional;

@Service 
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectService projectService;
    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final ContributorsRepository contributorsRepository;

    public TaskService(TaskRepository taskRepository, ProjectService projectService, ProjectRepository projectRepository, 
        StageRepository stageRepository, ContributorsRepository contributorsRepository) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.projectRepository = projectRepository;
        this.stageRepository = stageRepository;
        this.contributorsRepository = contributorsRepository;
    }

    @Transactional 
    public TaskResponse createTask(TaskRequest taskRequest, String projectId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        
        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        UserModel assignedUser = null;

        if (taskRequest.emailAssignedTo() != null && !taskRequest.emailAssignedTo().isEmpty()) {
            assignedUser = contributorsRepository.findUserByEmailAndProject(taskRequest.emailAssignedTo(), project)
                    .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found in the project"));
        }

        StageModel stage = stageRepository.findByIdAndProjectId(taskRequest.stage(), projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        TaskModel task = new TaskModel(taskRequest.title(), taskRequest.description(), taskRequest.priority(), user, assignedUser, stage);
        taskRepository.save(task);

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getPriority().name(), 
                task.getCreatedBy().getEmail(), task.getAssignedTo() != null ? task.getAssignedTo().getEmail() : null);
    }

    @Transactional 
    public void changeStage(TaskChangeStageRequest taskRequest, String projectId, String taskId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        UUID taskUUID = UUID.fromString(taskId);
        
        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        TaskModel task = findTaskInProject(taskUUID, project);

        if(task.getAssignedTo() == null || !task.getAssignedTo().equals(user)) {
            projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));
        }

        StageModel stage = stageRepository.findByIdAndProjectId(taskRequest.stageId(), projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        task.setStage(stage);
        taskRepository.save(task);
    }

    @Transactional 
    public TaskResponse updateTaks(String projectId, String taskId ,UserModel user, TaskUpdateRequest taskUpdate){
        UUID projectUUID = UUID.fromString(projectId);
        UUID taskUUID = UUID.fromString(taskId);

        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        TaskModel task = findTaskInProject(taskUUID, project);
        
        task.setTitle(taskUpdate.title());
        task.setDescription(taskUpdate.description());
        task.setPriority(taskUpdate.priority());

        taskRepository.save(task);

        return new TaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.getPriority().name(), 
                task.getCreatedBy().getEmail(), task.getAssignedTo() != null ? task.getAssignedTo().getEmail() : null);
    }

    @Transactional
    public void changeAssignedTo(String projectId, String taskId, UserModel user, TaskChangeAssignedToRequest taskRequest) {
        UUID projectUUID = UUID.fromString(projectId);
        UUID taskUUID = UUID.fromString(taskId);

        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        TaskModel task = findTaskInProject(taskUUID, project);

        UserModel assignedUser = contributorsRepository.findUserByEmailAndProject(taskRequest.emailAssignedTo(), project)
                .orElseThrow(() -> new ResourceNotFoundException("Assigned user not found in the project"));

        task.setAssignedTo(assignedUser);
        taskRepository.save(task);
    }

    @Transactional
    public void deleteTask(String projectId, String taskId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        UUID taskUUID = UUID.fromString(taskId);

        ProjectModel project = projectRepository.findById(projectUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        TaskModel task = findTaskInProject(taskUUID, project);
        taskRepository.delete(task);
    }

    private TaskModel findTaskInProject(UUID taskUUID, ProjectModel project) {
        TaskModel task = taskRepository.findById(taskUUID)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (task.getStage() == null || task.getStage().getProject() == null
                || !task.getStage().getProject().getId().equals(project.getId())) {
            throw new ResourceNotFoundException("Task does not belong to the specified project");
        }

        return task;
    }
    
}
