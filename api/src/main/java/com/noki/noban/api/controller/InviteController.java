package com.noki.noban.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.dto.response.InviteResponse;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.InviteService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@RestController 
@RequestMapping("/api/invites")
public class InviteController {
    
    private final InviteService inviteService;

    public InviteController(InviteService inviteService) {
        this.inviteService = inviteService;
    }

    @GetMapping("/my")
    public ResponseEntity<?> getMyInvites() {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<InviteResponse> invites = inviteService.listUserInvites(userDetails.getUser());
        return ResponseEntity.ok(invites);
    }

    @PostMapping("/accept/{inviteId}")
    public ResponseEntity<Void> acceptInvite(@PathVariable Integer inviteId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        inviteService.acceptInvite(inviteId, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/decline/{inviteId}")
    public ResponseEntity<Void> declineInvite(@PathVariable Integer inviteId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        inviteService.declineInvite(inviteId, userDetails.getUser());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{inviteId}")
    public ResponseEntity<Void> deleteInvite(@PathVariable Integer inviteId) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        inviteService.deleteInvite(inviteId, userDetails.getUser());

        return ResponseEntity.ok().build();
    }
}
