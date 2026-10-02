package com.noki.noban.api.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noki.noban.api.dto.projection.UserSummaryProject;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;

public interface ProjectRepository extends JpaRepository<ProjectModel, UUID> {
    
    @Query("SELECT p FROM ProjectModel p JOIN p.contributors c WHERE c.user = :user")
    Page<ProjectModel> findProjectsByUser(@Param(value = "user") UserModel user, Pageable pageable);

    @Query("""
        SELECT 
        new com.noki.noban.api.dto.projection.UserSummaryProject(
            u.id,
            u.name,
            u.email,
            c.role
        ) 
        FROM ProjectModel p JOIN p.contributors c JOIN c.user u WHERE p = :project
        """
    )
    Page<UserSummaryProject> findUsersByProject(@Param(value = "project") ProjectModel project, Pageable pageable);
}
