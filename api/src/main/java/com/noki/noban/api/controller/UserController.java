package com.noki.noban.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noki.noban.api.controller.docs.UserControllerDocs;
import com.noki.noban.api.dto.request.UserChangeEmailRequest;
import com.noki.noban.api.dto.request.UserChangeNameRequest;
import com.noki.noban.api.dto.request.UserChangePasswordRequest;
import com.noki.noban.api.dto.response.UserResponse;
import com.noki.noban.api.security.user.CustomUserDetails;
import com.noki.noban.api.services.UserService;

import jakarta.validation.Valid;

@RestController 
@RequestMapping("/api/users")
public class UserController implements UserControllerDocs {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserResponse response = new UserResponse(user.getUser().getId(), user.getUser().getEmail(), user.getUser().getName());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/email")
    public ResponseEntity<UserResponse> updateEmail(@RequestBody @Valid UserChangeEmailRequest userChangeEmailRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.updateEmail(user.getUser(), userChangeEmailRequest);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<UserResponse> updatePassword(@RequestBody @Valid UserChangePasswordRequest userChangePasswordRequest) {
        
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.updatePassword(user.getUser(), userChangePasswordRequest);

        return ResponseEntity.ok().build();
    }

    @PatchMapping("/name")
    public ResponseEntity<UserResponse> updateName(@RequestBody @Valid UserChangeNameRequest userChangeNameRequest) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.updateName(user.getUser(), userChangeNameRequest);

        return ResponseEntity.ok().build();
    }
}
