package com.noki.noban.api.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.noki.noban.api.models.TaskModel;

public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    
}
