package com.noki.noban.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noki.noban.api.models.UserModel;

public interface UserRepository extends JpaRepository<UserModel, UUID> {
    
    @Query("SELECT u FROM UserModel u WHERE u.email = :email")
    @EntityGraph(attributePaths = "roles")
    public UserModel findByEmailWithRole(String email);
}
