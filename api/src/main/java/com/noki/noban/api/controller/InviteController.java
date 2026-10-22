package com.noki.noban.api.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.InviteService;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

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
    public ResponseEntity<?> getMyInvites(
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

        Sort sort = Sort.by(direction, "i.status");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(inviteService.listUserInvites(userDetails.getUser(), pageRequest));
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
