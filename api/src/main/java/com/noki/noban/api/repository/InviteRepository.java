package com.noki.noban.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.noki.noban.api.models.InviteModel;
import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.UserModel;

public interface InviteRepository extends JpaRepository<InviteModel, Integer>{

    @Query("SELECT i FROM InviteModel i WHERE i.project = :project")
	List<InviteModel> findByProject(ProjectModel project);
    
    @Query("SELECT i FROM InviteModel i WHERE i.receiver = :receiver")
    List<InviteModel> findByReceiver(UserModel receiver);
}
