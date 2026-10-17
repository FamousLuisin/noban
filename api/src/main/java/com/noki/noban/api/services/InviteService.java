package com.noki.noban.api.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.request.InviteRequest;
import com.noki.noban.api.dto.response.InviteResponse;
import com.noki.noban.api.exceptions.ResourceNotFoundException;
import com.noki.noban.api.models.ContributorsId;
import com.noki.noban.api.models.ContributorsModel;
import com.noki.noban.api.models.InviteModel;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.ContributorsModel.RoleType;
import com.noki.noban.api.repository.ContributorsRepository;
import com.noki.noban.api.repository.InviteRepository;
import com.noki.noban.api.repository.ProjectRepository;
import com.noki.noban.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class InviteService {
    
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final InviteRepository inviteRepository;
    private final ProjectService projectService;
    private final ContributorsRepository contributorsRepository;

    public InviteService(UserRepository userRepository, ProjectRepository projectRepository, InviteRepository inviteRepository, ProjectService projectService, ContributorsRepository contributorsRepository) {
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.inviteRepository = inviteRepository;
        this.projectService = projectService;
        this.contributorsRepository = contributorsRepository;
    }

    @Transactional 
    public void createInvite(InviteRequest inviteRequest, String projectId, UserModel sender) {
        UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findById(projectUUID)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        UserModel receiver = userRepository.findByEmail(inviteRequest.receiver())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        contributorsRepository.findById(new ContributorsId(receiver, project))
            .ifPresent(contributor -> {
                throw new IllegalArgumentException("User is already a contributor of this project");
            });
        
        InviteModel invite = new InviteModel(
            inviteRequest.message(),
            InviteModel.Status.PENDING,
            project,
            sender,
            receiver,
            inviteRequest.role()
        );
        inviteRepository.save(invite);
    }

    public List<InviteResponse> listProjectInvites(String projectId, UserModel user) {
        UUID projectUUID = UUID.fromString(projectId);
        ProjectModel project = projectRepository.findById(projectUUID)
            .orElseThrow(() -> new ResourceNotFoundException("Project not found"));
        
        projectService.projectPermissionWithRole(project, user, List.of(RoleType.OWNER, RoleType.ADMIN));
        
        List<InviteModel> invites = inviteRepository.findByProject(project);

        return invites.stream()
            .map(invite -> new InviteResponse(
                invite.getId(),
                invite.getProject().getName(),
                invite.getMessage(),
                invite.getReceiver().getEmail(),
                invite.getSender().getEmail(),
                invite.getRole().toString(),
                invite.getSentAt(),
                invite.getStatus().toString()
            )).toList();
    }

    public List<InviteResponse> listUserInvites(UserModel user) {
        List<InviteModel> invites = inviteRepository.findByReceiver(user);

        return invites.stream()
            .map(invite -> new InviteResponse(
                invite.getId(),
                invite.getProject().getName(),
                invite.getMessage(),
                invite.getReceiver().getEmail(),
                invite.getSender().getEmail(),
                invite.getRole().toString(),
                invite.getSentAt(),
                invite.getStatus().toString()
            )).toList();
    }

    @Transactional 
    public void acceptInvite(Integer inviteId, UserModel user) {
        InviteModel invite = inviteRepository.findById(inviteId)
            .orElseThrow(() -> new ResourceNotFoundException("Invite not found"));
        
        if (!invite.getReceiver().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not authorized to accept this invite");
        }

        if (invite.getStatus() != InviteModel.Status.PENDING) {
            throw new IllegalArgumentException("Invite is not pending");
        }
        
        invite.setStatus(InviteModel.Status.ACCEPTED);
        inviteRepository.save(invite);

        ContributorsModel contributor = new ContributorsModel(
            invite.getReceiver(),
            invite.getProject(),
            invite.getRole()
        );
        contributorsRepository.save(contributor);
    }

    @Transactional 
    public void declineInvite(Integer inviteId, UserModel user) {
        InviteModel invite = inviteRepository.findById(inviteId)
            .orElseThrow(() -> new ResourceNotFoundException("Invite not found"));
        
        if (!invite.getReceiver().getId().equals(user.getId())) {
            throw new IllegalArgumentException("You are not authorized to decline this invite");
        }

        if (invite.getStatus() != InviteModel.Status.PENDING) {
            throw new IllegalArgumentException("Invite is not pending");
        }
        
        invite.setStatus(InviteModel.Status.DECLINED);
        inviteRepository.save(invite);
    }

    @Transactional 
    public void deleteInvite(Integer inviteId, UserModel user) {
        InviteModel invite = inviteRepository.findById(inviteId)
            .orElseThrow(() -> new ResourceNotFoundException("Invite not found"));

        projectService.projectPermissionWithRole(invite.getProject(), user, List.of(RoleType.OWNER, RoleType.ADMIN));
        
        inviteRepository.delete(invite);
    }
}
