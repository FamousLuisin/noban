package com.noki.noban.api.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.noki.noban.api.models.ProjectModel;
import com.noki.noban.api.models.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    
    @Query("SELECT t FROM TaskModel t WHERE t.stage.project = :project")
    List<TaskModel> findTaskByProject(@Param("project") ProjectModel project);
}
