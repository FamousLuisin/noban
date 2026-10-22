package com.noki.noban.api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noki.noban.api.models.InviteModel;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;

public interface InviteRepository extends JpaRepository<InviteModel, Integer>{

    @Query("SELECT i FROM InviteModel i WHERE i.project = :project")
	Page<InviteModel> findByProject(ProjectModel project, PageRequest pageRequest);
    
    @Query("SELECT i FROM InviteModel i WHERE i.receiver = :receiver")
    Page<InviteModel> findByReceiver(UserModel receiver, PageRequest pageRequest);
}
