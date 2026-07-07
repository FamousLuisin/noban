package com.noki.noban.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noki.noban.api.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    
    public UserModel findByEmail(String email);
}
