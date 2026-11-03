package com.noki.noban.api.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.noki.noban.api.dto.request.UserChangeEmailRequest;
import com.noki.noban.api.dto.request.UserChangeNameRequest;
import com.noki.noban.api.dto.request.UserChangePasswordRequest;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional 
    public void updateEmail(UserModel user, UserChangeEmailRequest userChangeEmailRequest) {
        user.setEmail(userChangeEmailRequest.email());
        userRepository.save(user);
    }

    @Transactional 
    public void updatePassword(UserModel user, UserChangePasswordRequest userChangePasswordRequest) {
        if (!passwordEncoder.matches(userChangePasswordRequest.oldPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(userChangePasswordRequest.newPassword()));
        userRepository.save(user);
    }

    @Transactional
    public void updateName(UserModel user, UserChangeNameRequest userChangeNameRequest) {
        user.setName(userChangeNameRequest.name());
        userRepository.save(user);
    }

}
