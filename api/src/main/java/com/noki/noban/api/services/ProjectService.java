package com.noki.noban.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.projection.UserSummaryProject;
import com.noki.noban.api.dto.request.ProjectRequest;
import com.noki.noban.api.dto.response.UserProjectsResponse;
import com.noki.noban.api.dto.response.ProjectResponse;
import com.noki.noban.api.dto.response.ProjectUsersResponse;
import com.noki.noban.api.dto.response.UserResponse;
import com.noki.noban.api.exceptions.PermissionDeniedException;
import com.noki.noban.api.exceptions.ResourceNotFoundException;
import com.noki.noban.api.models.ContributorsModel;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.ContributorsModel.RoleType;
import com.noki.noban.api.repository.ContributorsRepository;
import com.noki.noban.api.repository.ProjectRepository;

import jakarta.transaction.Transactional;

@Service
public class ProjectService {
    
    private ProjectRepository projectRepository;
    private ContributorsRepository contributorsRepository;

    public ProjectService(ProjectRepository projectRepository, ContributorsRepository contributorsRepository){
        this.projectRepository = projectRepository;
        this.contributorsRepository = contributorsRepository;
    }

    @Transactional 
    public ProjectResponse createProject(ProjectRequest request, UserModel user){
        ProjectModel newProject = new ProjectModel(request.name(), request.description(), user);

        newProject = projectRepository.saveAndFlush(newProject);

        ContributorsModel newContributors = new ContributorsModel(
            user, newProject, ContributorsModel.RoleType.OWNER);
        
        newContributors = contributorsRepository.save(newContributors);

        System.out.println("createdAt: " + newProject.getCreatedAt());

        return new ProjectResponse(
            newProject.getId(), 
            newProject.getName(), 
            newProject.getDescription(), 
            newProject.getCreatedAt(), 
            newProject.getUpdatedAt(),
            new UserResponse(user.getId(), user.getName(), user.getEmail()) 
        );
    }

    public UserProjectsResponse listMyProjects(UserModel user, PageRequest pageRequest) {
        Page<ProjectModel> projects = projectRepository.findProjectsByUser(user, pageRequest);

        List<ProjectResponse> listProjects = projects.getContent().stream().map(p -> {
            return new ProjectResponse(
                p.getId(), 
                p.getName(), 
                p.getDescription(), 
                p.getCreatedAt(), 
                p.getUpdatedAt(), 
                new UserResponse(p.getOwner().getId(), p.getOwner().getName(), p.getOwner().getEmail()));
        }).toList();

        UserResponse userResponse = new UserResponse(user.getId(), user.getName(), user.getEmail());

        return new UserProjectsResponse(
            userResponse, 
            listProjects, 
            projects.getPageable().getPageNumber(), 
            projects.getSize(), 
            projects.hasNext(),
            projects.getTotalElements(),
            projects.getTotalPages()
        );
    }

    public ProjectUsersResponse listProjectUsers(String id, PageRequest pageRequest, UserModel user) {
        UUID uuid = UUID.fromString(id);
        ProjectModel project = projectRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        commonProjectPermission(project, user);

        Page<UserSummaryProject> pageUsers = projectRepository.findUsersByProject(project, pageRequest);
        
        return new ProjectUsersResponse(
            new ProjectResponse(
                uuid, 
                project.getName(), 
                project.getDescription(), 
                project.getCreatedAt(), 
                project.getUpdatedAt(), 
                new UserResponse(project.getOwner().getId(), project.getOwner().getName(), project.getOwner().getEmail())), 
            pageUsers.getContent(),
            pageUsers.getPageable().getPageNumber(), 
            pageUsers.getSize(), 
            pageUsers.hasNext(),
            pageUsers.getTotalElements(),
            pageUsers.getTotalPages()
        );
    }

    @Transactional 
    public ProjectResponse updateProject(String id, ProjectRequest request, UserModel user) {
        UUID uuid = UUID.fromString(id);
        ProjectModel project = projectRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));

        project.setName(request.name());
        project.setDescription(request.description());

        project = projectRepository.save(project);

        return new ProjectResponse(
            project.getId(), 
            project.getName(), 
            project.getDescription(), 
            project.getCreatedAt(), 
            project.getUpdatedAt(), 
            new UserResponse(project.getOwner().getId(), project.getOwner().getName(), project.getOwner().getEmail()));
    }

    @Transactional 
    public void deleteProject(String id, UserModel user) {
        UUID uuid = UUID.fromString(id);
        ProjectModel project = projectRepository.findById(uuid).orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        projectPermissionWithRole(project, user, List.of(RoleType.OWNER));
        
        projectRepository.delete(project);
    }

    private void commonProjectPermission(ProjectModel project, UserModel user){
        Boolean isPermited = contributorsRepository.existsByProjectAndUser(project, user);
        if (!isPermited)
            throw new PermissionDeniedException("missing permission");
    }

    private void projectPermissionWithRole(ProjectModel project, UserModel user, List<RoleType> roles){
        Boolean isPermited = contributorsRepository.existsByProjectAndUserAndRoleIn(project, user, roles);
        if (!isPermited)
            throw new PermissionDeniedException("Missing permission");
    }
}
