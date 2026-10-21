package com.noki.noban.api.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noki.noban.api.models.ContributorsId;
import com.noki.noban.api.models.ContributorsModel;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;
import com.noki.noban.api.models.ContributorsModel.RoleType;

public interface ContributorsRepository extends JpaRepository<ContributorsModel, ContributorsId> {
    
    boolean existsByProjectAndUser(ProjectModel project, UserModel user);

    boolean existsByProjectAndUserAndRoleIn(ProjectModel project, UserModel user, Collection<RoleType> role);

    @Query("SELECT c.user FROM ContributorsModel c WHERE c.project = :project AND c.user.email = :email")
    Optional<UserModel> findUserByEmailAndProject(String email, ProjectModel project);
}
